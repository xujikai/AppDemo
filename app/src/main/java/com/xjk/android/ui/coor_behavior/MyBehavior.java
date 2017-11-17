package com.xjk.android.ui.coor_behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.xjk.android.R;

/**
 * Created by xxx on 2017/9/22.
 */

public class MyBehavior extends CoordinatorLayout.Behavior {

    public MyBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.dependency;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setY(dependency.getY() + child.getHeight());
        return true;
    }

}
