package com.xjk.android.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.xjk.android.R;
import com.xjk.android.utils.SvgPathParser;

/**
 * Created by xxx on 2017/1/3.
 */

public class WowSplashView extends View {

    public static final float SCALE = 2f;

    private Paint mPaint;
    private Path mTowerPath;
    private Path mTowerDst;
    private PathMeasure mTowerPathMeasure;
    private float mLength;

    //屏幕的宽
    private int mWidth;
    // from the svg file
    private int mTowerHeight = 600;
    private int mTowerWidth = 440;

    /**
     * 绘制铁塔
     */
    private float mAnimatorValue;
    private boolean isAnimateEnd;
    private int mAlpha;
    private long mDuration = 2000;
    private OnEndListener mListener;

    /**
     * 绘制文字
     */
    private String mTitle = "AndroidWing";
    private float mTitleY;
    private float mFinalTitleY = mTowerHeight + 100;

    public WowSplashView(Context context) {
        this(context, null);
    }

    public WowSplashView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WowSplashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundResource(R.color.colorPrimary);
        setDrawingCacheEnabled(true);

        /**
         * 初始化铁塔
         */
        mPaint = new Paint();
        mTowerPath = new SvgPathParser().parsePath(getResources().getString(R.string.path_tower));
        mTowerPathMeasure = new PathMeasure(mTowerPath, true);
        mLength = mTowerPathMeasure.getLength();
        mTowerDst = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.scale(SCALE, SCALE);
        float transX = (mWidth - mTowerWidth * SCALE) / 2 - 80;
        float transY = 100;
        canvas.translate(transX, transY);

        mTowerDst.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);

        float stop = mAnimatorValue * mLength;
        mTowerPathMeasure.getSegment(0, stop, mTowerDst, true);

        drawTower(canvas);
        drawTitle(canvas);
    }

    private void drawTower(Canvas canvas) {
        canvas.drawPath(mTowerDst, mPaint);

        if (isAnimateEnd) {
            mPaint.setAlpha(mAlpha);
            canvas.drawPath(mTowerPath, mPaint);
        }
    }

    private void drawTitle(Canvas canvas) {
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(80);

        int length = (int) mPaint.measureText(mTitle);
        int x = (mTowerWidth - length) / 2;
        canvas.drawText(mTitle, x, mTitleY, mPaint);
    }

    public void startAnimate() {
        getTowerValueAnimator().start();

        getTitleAnimator().start();
    }

    /**
     * 铁塔动画
     */
    private ValueAnimator getTowerValueAnimator() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                postInvalidateDelayed(10);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimateEnd = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimateEnd = true;
                getAlphaAnimator().start();
                valueAnimator.removeAllUpdateListeners();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(mDuration);
        return valueAnimator;
    }

    /**
     * 标题动画
     */
    private ValueAnimator getTitleAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mFinalTitleY);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTitleY = (float) valueAnimator.getAnimatedValue();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(mDuration);
        return valueAnimator;
    }

    private ValueAnimator getAlphaAnimator() {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 255);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAlpha = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                valueAnimator.removeAllUpdateListeners();
                if (mListener != null) {
                    mListener.onEnd(WowSplashView.this);
                }
            }
        });
        valueAnimator.setDuration(500);
        return valueAnimator;
    }

    public interface OnEndListener {
        void onEnd(WowSplashView wowSplashView);
    }

    public void setOnEndListener(OnEndListener listener) {
        mListener = listener;
    }

}
