package com.xjk.android.ui.image_handle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.xjk.android.utils.L;

import java.lang.ref.WeakReference;

/**
 * 自定义圆形，圆角图片
 * 通过Xfermode实现
 *
 * Created by xxx on 2017/7/21.
 */

public class XfermodeImageView extends AppCompatImageView{

    private Paint mPaint;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;
    private WeakReference<Bitmap> mFinalBitmap;

    public static final int TYPE_CIRCLE = 1;// 圆形
    public static final int TYPE_ROUND = 2;// 圆角
    private int mType = TYPE_CIRCLE;

    private Drawable drawable;
    private Bitmap bitmap;
    private Canvas dCanvas;

    public XfermodeImageView(Context context) {
        this(context, null, 0);
    }

    public XfermodeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(mType == TYPE_CIRCLE){
            int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(width, width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        bitmap = mFinalBitmap == null ? null : mFinalBitmap.get();

        if(bitmap == null || bitmap.isRecycled()){
            drawable = getDrawable();
            int dWidth = drawable.getIntrinsicWidth();
            int dHeight = drawable.getIntrinsicHeight();
            L.e("dWidth = " + dWidth + "，dHeight = " + dHeight);
            if(drawable != null){
                if(bitmap == null) bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                float scale;
                dCanvas = new Canvas(bitmap);
                if(mType == TYPE_ROUND){
                    scale = Math.max(getWidth() * 1.0f / dWidth, getHeight() * 1.0f / dHeight);
                }else {
                    scale = getWidth() * 1.0f / Math.min(dWidth, dHeight);
                }
                L.e("getWidth = " + getWidth() + "，getHeight = " + getHeight());
                drawable.setBounds(0, 0, (int)(dWidth * scale), (int)(dHeight * scale));
                drawable.draw(dCanvas);

                if(mMaskBitmap == null || mMaskBitmap.isRecycled()){
                    mMaskBitmap = getMaskBitmap();
                }
                mPaint.reset();
                mPaint.setXfermode(mXfermode);
                dCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
                mPaint.setXfermode(null);

                mFinalBitmap = new WeakReference<>(bitmap);
            }
        }

        if(bitmap != null){
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

    }

    @Override
    public void invalidate() {
        super.invalidate();
        mFinalBitmap = null;
        if(mMaskBitmap != null){
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
    }

    private Bitmap getMaskBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if(mType == TYPE_ROUND){
            RectF rect = new RectF(0, 0, getWidth(), getHeight());
            canvas.drawRoundRect(rect, 50f, 50f, paint);
        }else {
            /*
                因为在测量方法中，判断如果为圆形，设置宽和高一样，所以可以直接使用宽作为半径
                Math.max(getWidth() / 2, getHeight() / 2)
             */
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
        }
        return bitmap;
    }

}
