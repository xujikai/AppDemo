package com.xjk.android.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xjk.android.R;

/**
 * Created by xxx on 2017/3/16.
 */

public class PullRefreshLayout extends ViewGroup {

    private static final String TAG = PullRefreshLayout.class.getSimpleName();

    private Context mContext;
    private View mHeader;
    private View mFooter;
    private TextView mHeaderText;
    private ImageView mHeaderArrow;
    private ProgressBar mHeaderProgressBar;
    private TextView mFooterText;
    private ProgressBar mFooterProgressBar;

    private int lastChildIndex;//最后一个子View的索引
    private int mLayoutContentHeight;
    private int mHeaderHeight;//头布局高度
    private int mFooterHeight;

    private Status mStatus = Status.NORMAL;
    private int mLastInterY;//拦截方法中Y坐标
    private int mLastTouchY;//触摸方法中Y坐标

    private OverScroller mScroller;
    private int mTouchSlop;
    private int mDuration = 1000;
    private OnRefreshListener mListener;
    //速度追踪器
    private VelocityTracker mVelocityTracker = null;

    private enum Status {
        NORMAL, TRY_REFRESH, REFRESHING, TRY_LOAD_MORE, LOADING
    }

    public interface OnRefreshListener {
        void onTouch(int dy, boolean isRelease);

        void onRefresh();

        void onLoadMore();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mListener = listener;
    }

    public PullRefreshLayout(Context context) {
        this(context, null);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Log.e(TAG, "init: ");
        this.mContext = context;
        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e(TAG, "onFinishInflate: ");
        lastChildIndex = getChildCount() - 1;
        addHeader();
        addFooter();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged: ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure: ");

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG, "onLayout: ");
        mLayoutContentHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeader) {
                child.layout(0, -child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
                mHeaderHeight = 100;
            } else if (child == mFooter) {
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                mFooterHeight = child.getHeight();
            } else {
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                if (i < getChildCount()) {
                    if (child instanceof ScrollView) {
                        mLayoutContentHeight += getMeasuredHeight();
                        continue;
                    }
                    mLayoutContentHeight += child.getMeasuredHeight();
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: ");
    }

    /** 速度追踪器使用 */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int index = ev.getActionIndex();
//        int action = ev.getActionMasked();
//        int pointerId = ev.getPointerId(index);
//
//        switch(action) {
//            case MotionEvent.ACTION_DOWN:
//                Log.d(TAG, "ACTION_DOWN");
//                if(mVelocityTracker == null) {
//                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
//                    mVelocityTracker = VelocityTracker.obtain();
//                } else {
//                    // Reset the velocity tracker back to its initial state.
//                    mVelocityTracker.clear();
//                }
//                // Add a user's movement to the tracker.
//                mVelocityTracker.addMovement(ev);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "ACTION_MOVE");
//                mVelocityTracker.addMovement(ev);
//                // When you want to determine the velocity, call
//                // computeCurrentVelocity(). Then call getXVelocity()
//                // and getYVelocity() to retrieve the velocity for each pointer ID.
//                mVelocityTracker.computeCurrentVelocity(1000);
//                // Log velocity of pixels per second
//                // Best practice to use VelocityTrackerCompat where possible.
//                Log.d(TAG, "X velocity: " +
//                        VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId));
//                Log.d(TAG, "Y velocity: " +
//                        VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId));
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                Log.d(TAG, "ACTION_UP");
//                // Return a VelocityTracker object back to be re-used by others.
//                mVelocityTracker.recycle();
//                mVelocityTracker = null;
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mStatus == Status.REFRESHING || mStatus == Status.LOADING) return false;

        boolean isIntercept = false;
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastInterY = mLastTouchY = y;
                finishScroller();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onInterceptTouchEvent: ACTION_MOVE getScrollY() ==> " + getScrollY());
                if(Math.abs(y - mLastInterY) < mTouchSlop) break;
                if (y > mLastInterY) {
                    View child = getChildAt(0);
                    isIntercept = getRefreshIntercept(child);
                    if (isIntercept) updateStatus(Status.TRY_REFRESH);
                } else if (y < mLastInterY) {
                    View child = getChildAt(lastChildIndex);
                    isIntercept = getLoadMoreIntercept(child);
                    if (isIntercept) updateStatus(Status.TRY_LOAD_MORE);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mListener != null) mListener.onTouch(getScrollY(),true);
                Log.e(TAG, "onInterceptTouchEvent: getScrollY() ==> " + getScrollY());
                break;
        }
        mLastInterY = y;
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mStatus == Status.REFRESHING || mStatus == Status.LOADING) return true;

        int mScrollY = getScrollY();//在抬手的一瞬间，此值到UP事件时会变为0，所以需记录
        Log.e(TAG, "onTouchEvent: getScrollY() ==> " + mScrollY);
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchY = y;
                finishScroller();
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mLastTouchY - y;
                if (getScrollY() >= 0 && dy >= 0) {//下拉加载
                    scrollBy(0, dy / 3);
                } else if (getScrollY() <= 0 && dy <= 0) {//上拉刷新
                    scrollBy(0, dy / 3);
                    if (mListener != null) mListener.onTouch(dy / 3,false);
                } else {
                    scrollBy(0, dy / 3);
                }
                beforeRefreshing();
                beforeLoadMore();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (getScrollY() <= -mHeaderHeight) {
                    releaseWithStatusRefresh();
                    if (mListener != null) mListener.onRefresh();
                } else if (getScrollY() >= mFooterHeight) {
                    releaseWithStatusLoadMore();
                    if (mListener != null) mListener.onLoadMore();
                } else {
                    releaseWithStatusTryRefresh();
                    releaseWithStatusTryLoadMore();
                }
                if (mListener != null) mListener.onTouch(mScrollY,true);
                break;
        }
        mLastTouchY = y;
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    private void initVelocityTracker(MotionEvent event) {
//        if(mVelocityTracker == null) {
//            mVelocityTracker = VelocityTracker.obtain();
//        } else {
//            mVelocityTracker.clear();
//        }
//        mVelocityTracker.addMovement(event);
    }

    private void addVelocityMovement(MotionEvent event) {
//        mVelocityTracker.addMovement(event);
//        // When you want to determine the velocity, call
//        // computeCurrentVelocity(). Then call getXVelocity()
//        // and getYVelocity() to retrieve the velocity for each pointer ID.
//        mVelocityTracker.computeCurrentVelocity(1000);
    }

    private void recycleVelocity() {
//        if(mVelocityTracker != null) mVelocityTracker.recycle();
    }

    /**
     * 开始滚动
     * @param startY    开始偏移坐标
     * @param dy        滑动偏移量
     */
    private void startScroller(int startY, int dy) {
        mScroller.startScroll(0, startY, 0, dy, mDuration);
        invalidate();
    }

    /**
     * 强制停止滚动
     */
    private void finishScroller() {
        mScroller.forceFinished(true);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 更新当前状态
     */
    private void updateStatus(Status status) {
        mStatus = status;
    }

    private void addHeader() {
        mHeader = LayoutInflater.from(mContext).inflate(R.layout.widget_pull_header, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 400);
        addView(mHeader, layoutParams);
        mHeaderText = (TextView) findViewById(R.id.header_text);
        mHeaderProgressBar = (ProgressBar) findViewById(R.id.header_progressbar);
        mHeaderArrow = (ImageView) findViewById(R.id.header_arrow);
    }

    private void addFooter() {
        mFooter = LayoutInflater.from(mContext).inflate(R.layout.widget_pull_footer, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mFooter, layoutParams);
        mFooterText = (TextView) findViewById(R.id.footer_text);
        mFooterProgressBar = (ProgressBar) findViewById(R.id.footer_progressbar);
    }

    /*汇总判断 刷新和加载是否拦截*/
    private boolean getRefreshIntercept(View child) {
        boolean intercept = false;

        if (child instanceof AdapterView) {
            intercept = adapterViewRefreshIntercept(child);
        } else if (child instanceof ScrollView) {
            intercept = scrollViewRefreshIntercept(child);
        } else if (child instanceof RecyclerView) {
            intercept = recyclerViewRefreshIntercept(child);
        }
        return intercept;
    }

    private boolean getLoadMoreIntercept(View child) {
        boolean intercept = false;

        if (child instanceof AdapterView) {
            intercept = adapterViewLoadMoreIntercept(child);
        } else if (child instanceof ScrollView) {
            intercept = scrollViewLoadMoreIntercept(child);
        } else if (child instanceof RecyclerView) {
            intercept = recyclerViewLoadMoreIntercept(child);
        }
        return intercept;
    }
    /*汇总判断 刷新和加载是否拦截*/

    /*具体判断各种View是否应该拦截*/
    // 判断AdapterView下拉刷新是否拦截
    private boolean adapterViewRefreshIntercept(View child) {
        boolean intercept = true;
        AdapterView adapterChild = (AdapterView) child;
        if (adapterChild.getFirstVisiblePosition() != 0
                || adapterChild.getChildAt(0).getTop() != 0) {
            intercept = false;
        }
        return intercept;
    }

    // 判断AdapterView加载更多是否拦截
    private boolean adapterViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        AdapterView adapterChild = (AdapterView) child;
        if (adapterChild.getLastVisiblePosition() == adapterChild.getCount() - 1 &&
                (adapterChild.getChildAt(adapterChild.getChildCount() - 1).getBottom() >= getMeasuredHeight())) {
            intercept = true;
        }
        return intercept;
    }

    // 判断ScrollView刷新是否拦截
    private boolean scrollViewRefreshIntercept(View child) {
        boolean intercept = false;
        if (child.getScrollY() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    // 判断ScrollView加载更多是否拦截
    private boolean scrollViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        ScrollView scrollView = (ScrollView) child;
        View scrollChild = scrollView.getChildAt(0);

        if (scrollView.getScrollY() >= (scrollChild.getHeight() - scrollView.getHeight())) {
            intercept = true;
        }
        return intercept;
    }

    // 判断RecyclerView刷新是否拦截
    private boolean recyclerViewRefreshIntercept(View child) {
        boolean intercept = false;

        RecyclerView recyclerView = (RecyclerView) child;
        if (recyclerView.computeVerticalScrollOffset() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    // 判断RecyclerView加载更多是否拦截
    private boolean recyclerViewLoadMoreIntercept(View child) {
        boolean intercept = false;

        RecyclerView recyclerView = (RecyclerView) child;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange()) {
            intercept = true;
        }

        return intercept;
    }
    /*具体判断各种View是否应该拦截*/

    /*修改header和footer的状态*/
    public void beforeRefreshing() {
        //计算旋转角度
        int scrollY = Math.abs(getScrollY());
        scrollY = scrollY > mHeaderHeight ? mHeaderHeight : scrollY;
        float angle = (float) (scrollY * 1.0 / mHeaderHeight * 180);
        mHeaderArrow.setRotation(angle);

        if (getScrollY() <= -mHeaderHeight) {
            mHeaderText.setText("松开刷新");
        } else {
            mHeaderText.setText("下拉刷新");
        }
    }

    public void beforeLoadMore() {
        if (getScrollY() >= mHeaderHeight) {
            mFooterText.setText("松开加载更多");
        } else {
            mFooterText.setText("上拉加载更多");
        }
    }

    public void refreshFinished() {
        startScroller(getScrollY(),-getScrollY());
        mHeaderText.setText("下拉刷新");
        mHeaderProgressBar.setVisibility(GONE);
        mHeaderArrow.setVisibility(VISIBLE);
        updateStatus(Status.NORMAL);
    }

    public void loadMoreFinished() {
        startScroller(getScrollY(),-getScrollY());
        mFooterText.setText("上拉加载");
        mFooterProgressBar.setVisibility(INVISIBLE);
        updateStatus(Status.NORMAL);
    }

    private void releaseWithStatusTryRefresh() {
        startScroller(getScrollY(),-getScrollY());
        mHeaderText.setText("下拉刷新");
        updateStatus(Status.NORMAL);
    }

    private void releaseWithStatusTryLoadMore() {
        startScroller(getScrollY(),-getScrollY());
        mFooterText.setText("上拉加载更多");
        updateStatus(Status.NORMAL);
    }

    private void releaseWithStatusRefresh() {
        startScroller(getScrollY(),-getScrollY()-mHeaderHeight);
        mHeaderProgressBar.setVisibility(VISIBLE);
        mHeaderArrow.setVisibility(GONE);
        mHeaderText.setText("正在刷新");
        updateStatus(Status.REFRESHING);
    }

    private void releaseWithStatusLoadMore() {
        startScroller(getScrollY(),-getScrollY()+mFooterHeight);
        mFooterText.setText("正在加载");
        mFooterProgressBar.setVisibility(VISIBLE);
        updateStatus(Status.LOADING);
    }

    /**
     * 获取数据刷新页面
     */
    public void onCompleted(){
        if(getScrollY() < 0){
            refreshFinished();
        }else {
            loadMoreFinished();
        }
    }
    /*修改header和footer的状态*/
}
