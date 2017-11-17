package com.xjk.android.ui.coor_behavior;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import com.xjk.android.R;
import com.xjk.android.utils.L;

/**
 * 向上滑动：scrollBy(0, 10);    getScrollY() > 0
 *
 * 向下滑动：scrollBy(0, -10);   getScrollY() < 0
 *
 * Created by xxx on 2017/9/21.
 */

public class StickLayout extends LinearLayout implements NestedScrollingParent{

    private static final String TAG = "StickLayout";

    private ImageView ivHeader;
    private TextView tvSticky;
    private RecyclerView rvRecyclerView;

    private OverScroller mScroller;
    private int mTopHeight;
    private int mMaxScrollY;

    public StickLayout(Context context) {
        this(context, null);
    }

    public StickLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        L.e(TAG, "init");
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        L.e(TAG, "onFinishInflate");
        ivHeader = (ImageView) findViewById(R.id.iv_header);
        tvSticky = (TextView) findViewById(R.id.tv_sticky);
        rvRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        L.e(TAG, "onMeasure");
        ViewGroup.LayoutParams params = rvRecyclerView.getLayoutParams();
        params.height = getMeasuredHeight() - tvSticky.getMeasuredHeight() - (mTopHeight - ivHeader.getScrollY());
        rvRecyclerView.setLayoutParams(params);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        L.e(TAG, "onSizeChanged：" + w + " " + h);
        mTopHeight = ivHeader.getMeasuredHeight();
        mMaxScrollY = mTopHeight;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        L.e(TAG, "onNestedPreScroll dy: " + dy + " getScrollY(): " + getScrollY());
        boolean hiddenTop = dy > 0 && getScrollY() < mMaxScrollY;

        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

        if(hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
//            if(getScrollY() < 0) {
//                scrollBy(0, -getScrollY());
//            }
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        L.e(TAG, "onNestedPreFling velocityY: " + velocityY + " getScrollY(): " + getScrollY());
        if(velocityY > 0 && getScrollY() < mMaxScrollY) {
            fling((int) velocityY, mMaxScrollY);
            return true;
        }
        if(velocityY < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1)) {
            fling((int) velocityY, 0);
            return true;
        }
        return false;
    }

    public void fling(int velocityY, int maxY){
        L.e("fling " + mScroller.getFinalY());
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, maxY);
//        mScroller.startScroll(0, getScrollY(), 0, mScroller.getFinalY() - getScrollY(), 2000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {
        if(y < 0) y = 0;
        if(y > mMaxScrollY) y = mMaxScrollY;
        if(y != getScrollY()) super.scrollTo(x, y);
    }
}
