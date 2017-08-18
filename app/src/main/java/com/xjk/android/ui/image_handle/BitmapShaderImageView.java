package com.xjk.android.ui.image_handle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by xxx on 2017/7/21.
 */

public class BitmapShaderImageView extends AppCompatImageView{

    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";

    private Paint mPaint;
    private BitmapShader mBitmapShader;
    private Matrix mMatrix;
    private int mRadius;

    public static final int TYPE_CIRCLE = 1;// 圆形
    public static final int TYPE_ROUND = 2;// 圆角
    private int mType = TYPE_ROUND;

    private Bitmap bitmap;

    public BitmapShaderImageView(Context context) {
        this(context, null, 0);
    }

    public BitmapShaderImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BitmapShaderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMatrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(mType == TYPE_CIRCLE){
            int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = width / 2;
            setMeasuredDimension(width, width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        Drawable drawable = getDrawable();
        if(drawable == null) return;
        setUpShader(drawable);

        if(mType == TYPE_ROUND){
            RectF rect = new RectF(0, 0, getWidth(), getWidth());
            canvas.drawRoundRect(rect, 50f, 50f, mPaint);
        }else {
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        }

    }

    private void setUpShader(Drawable drawable){
        int dWidth = drawable.getIntrinsicWidth();
        int dHeight = drawable.getIntrinsicHeight();
        bitmap = Bitmap.createBitmap(dWidth, dHeight, Bitmap.Config.ARGB_8888);
        drawable.setBounds(0, 0, dWidth, dHeight);
        Canvas dCanvas = new Canvas(bitmap);
        drawable.draw(dCanvas);

        float scale;
        if(mType == TYPE_ROUND){
            scale = Math.max(getWidth() * 1.0f / dWidth, getHeight() * 1.0f / dHeight);
        }else {
            scale = getWidth() * 1.0f / Math.min(dWidth, dHeight);
        }
        mMatrix.setScale(scale, scale);

        if(mBitmapShader == null) mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, mType);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
            mType = bundle.getInt(STATE_TYPE);
        }else {
            super.onRestoreInstanceState(state);
        }
    }
}
