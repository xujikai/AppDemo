package com.xjk.android.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.xjk.android.R;
import com.xjk.android.ui.glide_trans.RoundedCornersTransformation;

/**
 * Created by xxx on 2016/7/4.
 */
public class ImageUtils {

    public static void setImageRes(Context context, ImageView view, int imageId) {
        Glide.with(context).load(imageId).crossFade().into(view);
    }

    public static void setImageRes(Context context, ImageView view, int imageId, Transformation... transformations) {
        Glide.with(context).load(imageId).crossFade().bitmapTransform(transformations).into(view);
    }

    public static void setImageUrl(Context context, ImageView view, String imageUrl) {
        setImageUrl(context,view,imageUrl,R.mipmap.test_avatar);
    }

    public static void setImageUrl(Context context, ImageView view, String imageUrl, int defResourceId) {
        Glide.with(context).load(imageUrl).crossFade().placeholder(defResourceId).into(view);
    }

    public static void setImageUrl(Context context, ImageView view, String imageUrl, Transformation... transformations) {
        setImageUrl(context,view,imageUrl,R.mipmap.test_avatar,transformations);
    }

    public static void setImageUrl(Context context, ImageView view, String imageUrl, int defResourceId, Transformation... transformations) {
        Glide.with(context).load(imageUrl).crossFade().placeholder(defResourceId).bitmapTransform(transformations).into(view);
    }

    public static void setRoundImageUrl(Context context, ImageView view, String imageUrl, int defResourceId) {
        setImageUrl(context,view,imageUrl,defResourceId,new CenterCrop(context), new RoundedCornersTransformation(context, UIUtils.dip2px(6), 0));
    }

    public static Drawable getMaskDrawable(int maskId) {
        Drawable drawable = UIUtils.getDrawable(maskId);
        if (drawable == null) {
            throw new IllegalArgumentException("maskId is invalid");
        }
        return drawable;
    }

}
