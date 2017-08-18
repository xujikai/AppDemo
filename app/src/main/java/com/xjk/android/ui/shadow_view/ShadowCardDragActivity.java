package com.xjk.android.ui.shadow_view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;
import com.xjk.android.utils.L;
import com.xjk.android.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by xxx on 2017/7/27.
 */

public class ShadowCardDragActivity extends BaseActivity{

    @BindView(R.id.card_parent)
    FrameLayout flCardParent;
    @BindView(R.id.shape_select)
    Button btnShapeSelect;
    @BindView(R.id.card)
    TextView tvCard;

    private ArrayList<Shape> mShapeList = new ArrayList<>();
    private ShapeDrawable mCardBackground = new ShapeDrawable();

    public static void start(Context context){
        Intent intent = new Intent(context, ShadowCardDragActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_shadow_card_drag;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {
        btnShapeSelect.setOnClickListener(new View.OnClickListener() {
            int pos;
            @Override
            public void onClick(View v) {
                pos = ++pos % mShapeList.size();
                mCardBackground.setShape(mShapeList.get(pos));
            }
        });

        flCardParent.setOnTouchListener(new View.OnTouchListener() {
            float downX;
            float downY;
            long downTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX() - tvCard.getTranslationX();
                        downY = event.getY() - tvCard.getTranslationY();
                        downTime = event.getDownTime();
//                        L.e("ACTION_DOWN：" + tvCard.getTranslationX() + " == " + tvCard.getTranslationY());
                        L.e("ACTION_DOWN：" + event.getX() + " == " + event.getY());

                        ObjectAnimator upAnim = ObjectAnimator.ofFloat(tvCard, "translationZ", UIUtils.dp2px(10));
                        upAnim.setDuration(100);
                        upAnim.setInterpolator(new DecelerateInterpolator());
                        upAnim.start();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        tvCard.setTranslationX(event.getX() - downX);
                        tvCard.setTranslationY(event.getY() - downY);
//                        L.e("ACTION_MOVE：" + tvCard.getTranslationX() + " == " + tvCard.getTranslationY());
                        L.e("ACTION_MOVE：" + event.getX() + " == " + event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        ObjectAnimator downAnim = ObjectAnimator.ofFloat(tvCard, "translationZ", 0);
                        downAnim.setDuration(100);
                        downAnim.setInterpolator(new AccelerateInterpolator());
                        downAnim.start();
//                        L.e("ACTION_UP：" + tvCard.getTranslationX() + " == " + tvCard.getTranslationY());
                        L.e("ACTION_UP：" + event.getX() + " == " + event.getY());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        mShapeList.add(new RectShape());
        mShapeList.add(new OvalShape());
        float radius = UIUtils.dp2px(10);
        float radii[] = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        mShapeList.add(new RoundRectShape(radii, null, null));
        mShapeList.add(new TriangleShape());

        mCardBackground.getPaint().setColor(Color.WHITE);
        mCardBackground.setShape(mShapeList.get(0));
        tvCard.setBackground(mCardBackground);
    }

    private static class TriangleShape extends Shape{

        private final Path mPath = new Path();

        @Override
        protected void onResize(float width, float height) {
            mPath.reset();
            mPath.moveTo(0, 0);
            mPath.lineTo(width, 0);
            mPath.lineTo(width / 2, height);
            mPath.close();
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            canvas.drawPath(mPath, paint);
        }

        @Override
        public void getOutline(@NonNull Outline outline) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                outline.setConvexPath(mPath);
            }
        }
    }

}
