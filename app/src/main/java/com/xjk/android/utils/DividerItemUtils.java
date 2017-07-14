package com.xjk.android.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.xjk.android.R;
import com.xjk.android.ui.widget.DividerItemGrid;
import com.xjk.android.ui.widget.DividerItemLinear;

/**
 * RecyclerView 条目分割线
 * Created by xxx on 2017/3/24.
 */

public class DividerItemUtils {

    /**
     * 垂直方向分割线
     */
    public static RecyclerView.ItemDecoration createLinearDivider(Context context, int drawableId) {
        DividerItemLinear dividerItemDecoration = new DividerItemLinear(
                context, DividerItemLinear.LINEAR_DIVIDER_VERTICAL);
        dividerItemDecoration.setDivider(UIUtils.getDrawable(drawableId));
        return dividerItemDecoration;
    }

    public static RecyclerView.ItemDecoration createLinearDivider(Context context) {
        return createLinearDivider(context,R.drawable.trans_rect_bg);
    }

    /**
     * 网格分割线
     */
    public static RecyclerView.ItemDecoration createGridDivider(int dividerWidth) {
        DividerItemGrid offsetsItemDecoration = new DividerItemGrid(DividerItemGrid.GRID_OFFSETS_VERTICAL);
        offsetsItemDecoration.setVerticalItemOffsets(dividerWidth);
        offsetsItemDecoration.setHorizontalItemOffsets(dividerWidth);
        return offsetsItemDecoration;
    }

    /**
     * 网格分割线
     */
    public static RecyclerView.ItemDecoration createGridDivider() {
        return createGridDivider((int) UIUtils.getDimension(R.dimen.d10));
    }

}
