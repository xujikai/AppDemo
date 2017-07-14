package com.xjk.android.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by xxx on 2017/1/10.
 */

public class HorizontalViewPager extends ViewPager {

    private Bitmap mBg;
    private Paint mPaint;

    public HorizontalViewPager(Context context) {
        this(context, null);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setBackground(Bitmap bitmap) {
        this.mBg = bitmap;
        this.mPaint.setFilterBitmap(true);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mBg != null) {
            int bgWidth = this.mBg.getWidth();
            int bgHeight = this.mBg.getHeight();
            int count = getAdapter().getCount();
            int x = getScrollX();
            int width = bgHeight * getWidth() / getHeight();

            int w = x * (bgWidth - width) / (count - 1) / getWidth();
            canvas.drawBitmap(mBg, new Rect(w, 0, width + w, bgHeight), new Rect(x, 0, x + getWidth(), getHeight()), this.mPaint);
        }
        super.dispatchDraw(canvas);
    }
}
