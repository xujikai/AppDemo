package com.xjk.android.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.xjk.android.R;

/**
 * Created by xxx on 2017/1/3.
 */

public class WowView extends View {

    private Paint mPaint;
    private Bitmap mSrcBitmap;
    private Bitmap mDstBitmap;
    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    private int mWidth;
    private int mHeight;

    private float mScale;
    private float mAlpha;
    private long mDuration = 2000;

    public WowView(Context context) {
        this(context, null);
    }

    public WowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mPaint = new Paint();
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_wow_splash);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDstBitmap != null) {
            canvas.drawBitmap(mDstBitmap, 0, 0, null);
            mPaint.setXfermode(mMode);
            canvas.scale(mScale, mScale, mWidth / 2, mHeight / 2);
            canvas.drawBitmap(mSrcBitmap, (mWidth - mSrcBitmap.getWidth()) / 2, (mHeight - mSrcBitmap.getHeight()) / 2, mPaint);
        }
        setAlpha(mAlpha);
    }

    public void setSrcBitmap(Bitmap srcBitmap) {
        this.mDstBitmap = srcBitmap;
        invalidate();
    }

    public void startAnimator(Bitmap srcBitmap) {
        setSrcBitmap(srcBitmap);
        getScaleValueAnimator().start();
        getAlphaValueAnimator().start();
    }

    private ValueAnimator getAlphaValueAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAlpha = (float) valueAnimator.getAnimatedValue();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(View.GONE);
            }
        });
        valueAnimator.setDuration(mDuration);
        return valueAnimator;
    }

    private ValueAnimator getScaleValueAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 6f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mScale = (float) valueAnimator.getAnimatedValue();
                postInvalidateDelayed(16);
            }
        });
        valueAnimator.setDuration(mDuration);
        return valueAnimator;
    }
}
