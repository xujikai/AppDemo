package com.xjk.android.ui.evaluator;

import android.animation.TypeEvaluator;

import com.xjk.android.module.Point;

/**
 * Created by xxx on 2016/7/1.
 */
public class PointEvaluator implements TypeEvaluator<Point> {

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        float x = startValue.getX() + fraction * (endValue.getX() - startValue.getX());
        float y = startValue.getY() + fraction * (endValue.getY() - startValue.getY());
        return new Point(x,y);
    }
}
