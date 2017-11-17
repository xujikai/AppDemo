package com.xjk.android.ui.coor_behavior;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by xxx on 2017/9/22.
 */

public class MyButton extends AppCompatButton{

    private int lastX;
    private int lastY;

    public MyButton(Context context) {
        this(context, null);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
                params.leftMargin = params.leftMargin + x - lastX;
                params.topMargin = params.topMargin + y - lastY;
                setLayoutParams(params);
                requestLayout();
                break;
        }

        lastX = x;
        lastY = y;
        return true;
    }

}
