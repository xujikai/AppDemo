package com.xjk.android.ui.widget;

import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;

import com.xjk.android.utils.L;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This class can only be used in the RecyclerView which use a GridLayoutManager
 * or StaggeredGridLayoutManager, but it's not always work for the StaggeredGridLayoutManager,
 * because we can't figure out which position should belong to the last column or the last row
 */
public class DividerItemGrid extends RecyclerView.ItemDecoration {
    public static final int GRID_OFFSETS_HORIZONTAL = GridLayoutManager.HORIZONTAL;
    public static final int GRID_OFFSETS_VERTICAL = GridLayoutManager.VERTICAL;

    private final SparseArray<OffsetsCreator> mTypeOffsetsFactories = new SparseArray<>();

    @IntDef({
            GRID_OFFSETS_HORIZONTAL,
            GRID_OFFSETS_VERTICAL
    })
    @Retention(RetentionPolicy.SOURCE)
    private @interface Orientation {
    }

    @Orientation
    private int mOrientation;
    private int mVerticalItemOffsets;
    private int mHorizontalItemOffsets;

    private boolean mIsOffsetEdge;
    private boolean mIsOffsetLast;

    public DividerItemGrid(@Orientation int orientation) {
        setOrientation(orientation);
        mIsOffsetLast = true;
        mIsOffsetEdge = true;
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public void setVerticalItemOffsets(int verticalItemOffsets) {
        this.mVerticalItemOffsets = verticalItemOffsets;
    }

    public void setHorizontalItemOffsets(int horizontalItemOffsets) {
        this.mHorizontalItemOffsets = horizontalItemOffsets;
    }

    public void setOffsetEdge(boolean isOffsetEdge) {
        this.mIsOffsetEdge = isOffsetEdge;
    }

    public void setOffsetLast(boolean isOffsetLast) {
        this.mIsOffsetLast = isOffsetLast;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int adapterPosition = parent.getChildAdapterPosition(view);

        int horizontalOffsets = getHorizontalOffsets(parent, view);
        int verticalOffsets = getVerticalOffsets(parent, view);

        boolean isFirstRow = isFirstRow(adapterPosition, spanCount, childCount);
        boolean isLastRow = isLastRow(adapterPosition, spanCount, childCount);
        boolean isFirstColumn = isFirstColumn(adapterPosition, spanCount, childCount);
        boolean isLastColumn = isLastColumn(adapterPosition, spanCount, childCount);

        outRect.set(0, 0, horizontalOffsets, verticalOffsets);
        outRect.bottom = mOrientation != GRID_OFFSETS_VERTICAL && isLastRow ? 0 : verticalOffsets;
        outRect.right = mOrientation != GRID_OFFSETS_HORIZONTAL && isLastColumn ? 0 : horizontalOffsets;

        if (mIsOffsetEdge) {
            outRect.top = isFirstRow ? verticalOffsets : 0;
            outRect.left = isFirstColumn ? horizontalOffsets : 0;
            outRect.right = horizontalOffsets;
            outRect.bottom = verticalOffsets;
        }

        if (!mIsOffsetLast) {
            if (mOrientation == GRID_OFFSETS_VERTICAL && isLastRow) {
                outRect.bottom = 0;
            } else if (mOrientation == GRID_OFFSETS_HORIZONTAL && isLastColumn) {
                outRect.right = 0;
            }
        }

        outRect.right = mOrientation != GRID_OFFSETS_HORIZONTAL && isFirstColumn ? horizontalOffsets / 2 : horizontalOffsets ;
        outRect.left = mOrientation != GRID_OFFSETS_HORIZONTAL && isLastColumn ? horizontalOffsets / 2 : horizontalOffsets;
        outRect.top = 0;

        // 矩形代表条目四周的具有的边界宽度
        L.e(outRect.toString() + " == " + adapterPosition);
        // 之前存在问题
        // Rect(56, 56 - 56, 56) == 0
        // Rect(0, 56 - 56, 56) == 1
        // Rect(56, 0 - 56, 56) == 2
        // Rect(0, 0 - 56, 56) == 3

        // 修正之后
        // Rect(56, 56 - 28, 56) == 0
        // Rect(28, 56 - 56, 56) == 1
        // Rect(56, 0 - 28, 56) == 2
        // Rect(28, 0 - 56, 56) == 3
    }

    private int getHorizontalOffsets(RecyclerView parent, View view) {
        if (mTypeOffsetsFactories.size() == 0) {
            return mHorizontalItemOffsets;
        }

        final int adapterPosition = parent.getChildAdapterPosition(view);
        final int itemType = parent.getAdapter().getItemViewType(adapterPosition);
        final OffsetsCreator offsetsCreator = mTypeOffsetsFactories.get(itemType);

        if (offsetsCreator != null) {
            return offsetsCreator.createHorizontal(parent, adapterPosition);
        }

        return 0;
    }

    private int getVerticalOffsets(RecyclerView parent, View view) {
        if (mTypeOffsetsFactories.size() == 0) {
            return mVerticalItemOffsets;
        }

        final int adapterPosition = parent.getChildAdapterPosition(view);
        final int itemType = parent.getAdapter().getItemViewType(adapterPosition);
        final OffsetsCreator offsetsCreator = mTypeOffsetsFactories.get(itemType);

        if (offsetsCreator != null) {
            return offsetsCreator.createVertical(parent, adapterPosition);
        }

        return 0;
    }

    private boolean isFirstColumn(int position, int spanCount, int childCount) {
        if (mOrientation == GRID_OFFSETS_VERTICAL) {
            return position % spanCount == 0;
        } else {
            int lastColumnCount = childCount % spanCount;
            lastColumnCount = lastColumnCount == 0 ? spanCount : lastColumnCount;
            return position < childCount - lastColumnCount;
        }
    }

    private boolean isLastColumn(int position, int spanCount, int childCount) {
        if (mOrientation == GRID_OFFSETS_VERTICAL) {
            return (position + 1) % spanCount == 0;
        } else {
            int lastColumnCount = childCount % spanCount;
            lastColumnCount = lastColumnCount == 0 ? spanCount : lastColumnCount;
            return position >= childCount - lastColumnCount;
        }
    }

    private boolean isFirstRow(int position, int spanCount, int childCount) {
        if (mOrientation == GRID_OFFSETS_VERTICAL) {
            return position < spanCount;
        } else {
            return position % spanCount == 0;
        }
    }

    private boolean isLastRow(int position, int spanCount, int childCount) {
        if (mOrientation == GRID_OFFSETS_VERTICAL) {
            int lastColumnCount = childCount % spanCount;
            lastColumnCount = lastColumnCount == 0 ? spanCount : lastColumnCount;
            return position >= childCount - lastColumnCount;
        } else {
            return (position + 1) % spanCount == 0;
        }
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else {
            throw new UnsupportedOperationException("the GridDividerItemDecoration can only be used in " +
                    "the RecyclerView which use a GridLayoutManager or StaggeredGridLayoutManager");
        }
    }

    public void registerTypeDrawable(int itemType, OffsetsCreator offsetsCreator) {
        mTypeOffsetsFactories.put(itemType, offsetsCreator);
    }

    public interface OffsetsCreator {
        int createVertical(RecyclerView parent, int adapterPosition);

        int createHorizontal(RecyclerView parent, int adapterPosition);
    }

}
