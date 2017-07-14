package com.xjk.android.ui.widget;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.xjk.android.module.Point;
import com.xjk.android.ui.evaluator.PointEvaluator;

/**
 * Created by xxx on 2016/7/1.
 */
public class BallView extends View {

    private int radius = 50;
    private Point mCurrPoint;
    private Paint mPaint;
    private String color;

    public BallView(Context context) {
        this(context,null);
    }

    public BallView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mCurrPoint == null){
            mCurrPoint = new Point(radius,radius);
            drawCircle(canvas);
            startAnimator();
        }else {
            drawCircle(canvas);
        }
    }

    private void startAnimator(){
        Point mEndPoint = new Point(getWidth() - radius,getHeight() - radius);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(),mCurrPoint,mEndPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(mPaint,"color",
                new ArgbEvaluator(),0xFF0000FF, 0xFFFF0000);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(mPaint,"color",
//                new ArgbEvaluator(),Color.BLUE, Color.RED);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(this,"color",
//                new ColorEvaluator(),"#0000FF", "#FF0000");
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator).with(objectAnimator);
        animSet.setDuration(5000);
        animSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        animSet.setInterpolator(new LinearInterpolator());
//        animSet.setInterpolator(new CycleInterpolator(2));
//        animSet.setInterpolator(new AnticipateOvershootInterpolator());
//        animSet.setInterpolator(new AnticipateInterpolator());
//        animSet.setInterpolator(new BounceInterpolator());
//        animSet.setInterpolator(new OvershootInterpolator());
        animSet.start();
    }

    private void drawCircle(Canvas canvas){
        canvas.drawCircle(mCurrPoint.getX(),mCurrPoint.getY(),radius,mPaint);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }
}
