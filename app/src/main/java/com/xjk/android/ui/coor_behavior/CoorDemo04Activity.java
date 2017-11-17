package com.xjk.android.ui.coor_behavior;

import android.content.Context;
import android.content.Intent;

import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;

/**
 * Created by xxx on 2017/9/22.
 */

public class CoorDemo04Activity extends BaseActivity{

    public static void start(Context context) {
        Intent intent = new Intent(context, CoorDemo04Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_coor_04;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}
