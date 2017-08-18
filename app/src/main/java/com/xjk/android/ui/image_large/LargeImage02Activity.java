package com.xjk.android.ui.image_large;

import android.content.Context;
import android.content.Intent;

import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;

/**
 * Created by xxx on 2017/7/20.
 */

public class LargeImage02Activity extends BaseActivity{

    @BindView(R.id.imageView)
    LargeImageView largeImageView;

    public static void start(Context context){
        Intent intent = new Intent(context, LargeImage02Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_image_large_02;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        try {
            InputStream inputStream = getAssets().open("image_large.jpg");
            largeImageView.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
