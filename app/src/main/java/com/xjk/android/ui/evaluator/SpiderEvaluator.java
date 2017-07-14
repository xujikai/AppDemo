package com.xjk.android.ui.evaluator;

import android.animation.TypeEvaluator;

/**
 * Created by xxx on 2016/7/6.
 */
public class SpiderEvaluator implements TypeEvaluator<double[]>{
    @Override
    public double[] evaluate(float fraction, double[] startValue, double[] endValue) {
        double[] doubles = new double[startValue.length];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = startValue[i] + fraction * (endValue[i] - startValue[i]);
        }
        return doubles;
    }
}
