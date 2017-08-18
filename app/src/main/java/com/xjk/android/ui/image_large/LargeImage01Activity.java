package com.xjk.android.ui.image_large;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.util.Log;
import android.widget.ImageView;

import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;

/**
 * Created by xxx on 2017/7/19.
 */

public class LargeImage01Activity extends BaseActivity{

    @BindView(R.id.imageView)
    ImageView ivImageView;

    public static void start(Context context){
        Intent intent = new Intent(context, LargeImage01Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_image_large_01;
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
            InputStream inputStream = getAssets().open("image_girl.jpg");

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            int outWidth = options.outWidth;
            int outHeight = options.outHeight;
            String outMimeType = options.outMimeType;
            Log.i(TAG, "initData: " + outWidth + " = " + outHeight + " = " + outMimeType);

            BitmapRegionDecoder regionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Rect rect = new Rect(outWidth / 2 - 100, outHeight / 2 - 100, outWidth / 2 + 100, outHeight / 2 + 100);
            Bitmap bitmap = regionDecoder.decodeRegion(rect, options);
            ivImageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
