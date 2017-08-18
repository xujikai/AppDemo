package com.xjk.android.ui.image_large;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xjk.android.utils.L;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xxx on 2017/7/19.
 */

public class LargeImageView extends View {

    private BitmapRegionDecoder mRegionDecoder;
    private int mImageWidth, mImageHeight;
    private Rect mRect = new Rect();

    private MoveGestureDetector mDetector;

    private BitmapFactory.Options mOptions;

    public LargeImageView(Context context) {
        this(context, null);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        mDetector = new MoveGestureDetector(context, new MoveGestureDetector.SimpleMoveGestureDetector() {
            @Override
            public boolean onMove(MoveGestureDetector detector) {
                int moveX = (int) detector.getMoveX();
                int moveY = (int) detector.getMoveY();
                L.e("moveX = " + moveX + "，moveY = " + moveY);

                if (mImageWidth > getWidth()) {
                    mRect.offset(-moveX, 0);
                    checkWidth();
                    invalidate();
                }
                L.e("mImageWidth = " + mImageWidth + "，width = " + getWidth());

                if (mImageHeight > getHeight()) {
                    mRect.offset(0, -moveY);
                    checkHeight();
                    invalidate();
                }
                L.e("mImageHeight = " + mImageHeight + "，height = " + getHeight());

                return true;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        mRect.left = mImageWidth / 2 - width / 2;
        mRect.top = mImageHeight / 2 - height / 2;
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap mCanvasBitmap = mRegionDecoder.decodeRegion(mRect, mOptions);
        canvas.drawBitmap(mCanvasBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    public void setInputStream(InputStream is) {
        try {
            mRegionDecoder = BitmapRegionDecoder.newInstance(is, false);
            BitmapFactory.Options tempOptions = new BitmapFactory.Options();
            tempOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, tempOptions);
            mImageWidth = tempOptions.outWidth;
            mImageHeight = tempOptions.outHeight;

            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkWidth() {
        if (mRect.right > mImageWidth) {
            mRect.right = mImageWidth;
            mRect.left = mImageWidth - getWidth();
        }

        if (mRect.left < 0) {
            mRect.left = 0;
            mRect.right = getWidth();
        }
    }

    private void checkHeight() {
        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = getHeight();
        }

        if (mRect.bottom > mImageHeight) {
            mRect.bottom = mImageHeight;
            mRect.top = mImageHeight - getHeight();
        }
    }

}
