package com.xjk.android.ui.shadow_view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;
import com.xjk.android.utils.UIUtils;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by xxx on 2017/7/26.
 */

public class ShadowCardStackActivity extends BaseActivity {

    private static final float X = UIUtils.dp2px(600);
    private static final float Y = UIUtils.dp2px(50);
    private static final float Z = UIUtils.dp2px(8);
    private static final float ROTATE_DEGREES = 30;

    @BindView(R.id.rl_card_group)
    RelativeLayout rlCardGroup;

    public static void start(Context context){
        Intent intent = new Intent(context, ShadowCardStackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_shadow_card_stack;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        ArrayList<Animator> expandAnimators = new ArrayList<>();// 展开动画
        ArrayList<Animator> towardAnimators = new ArrayList<>();// Z轴向上移动动画
        ArrayList<Animator> moveAwayAnimators = new ArrayList<>();
        ArrayList<Animator> moveBackAnimators = new ArrayList<>();
        ArrayList<Animator> awayAnimators = new ArrayList<>();// Z轴向下移动动画
        ArrayList<Animator> collapseAnimators = new ArrayList<>();// 收缩动画

        final int max = rlCardGroup.getChildCount();
        for (int i = 0; i < max; i++) {
            TextView card = (TextView) rlCardGroup.getChildAt(i);
            card.setText(String.format(Locale.CHINA, "CardNumber: %d", i + 1));

            float targetY = (i - (max - i) / 2.0f) * Y;
            Animator expand = ObjectAnimator.ofFloat(card, "translationY", targetY);
            expand.setDuration(500);
            expandAnimators.add(expand);

            Animator toward = ObjectAnimator.ofFloat(card, "translationZ", i * Z);
            toward.setStartDelay(200 * (max - i));
            towardAnimators.add(toward);

            card.setPivotX(UIUtils.dp2px(150));
            Animator transAway = ObjectAnimator.ofFloat(card, "translationX", i == 0 ? 0 : X);
            transAway.setStartDelay(200 * (max - i));
            moveAwayAnimators.add(transAway);
            Animator rotateAway = ObjectAnimator.ofFloat(card, "rotationY", i == 0 ? 0 : ROTATE_DEGREES);
            rotateAway.setStartDelay(200 * (max - i));
            moveAwayAnimators.add(rotateAway);

            Animator transBack = ObjectAnimator.ofFloat(card, "translationX", 0);
            transBack.setStartDelay(200 * i);
            moveBackAnimators.add(transBack);
            Animator rotateBack = ObjectAnimator.ofFloat(card, "rotationY", 0);
            rotateBack.setStartDelay(200 * i);
            moveBackAnimators.add(rotateBack);

            Animator away = ObjectAnimator.ofFloat(card, "translationZ", 0);
            away.setStartDelay(200 * i);
            awayAnimators.add(away);

            Animator collapse = ObjectAnimator.ofFloat(card, "translationY", 0);
            collapse.setDuration(500);
            collapseAnimators.add(collapse);
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(
                createAnimatorSet(expandAnimators, 250),
                createAnimatorSet(towardAnimators, 0),

                createAnimatorSet(moveAwayAnimators, 250),
                createAnimatorSet(moveBackAnimators, 250),

                createAnimatorSet(awayAnimators, 2000),
                createAnimatorSet(collapseAnimators, 0));
        animatorSet.start();
    }

    public AnimatorSet createAnimatorSet(ArrayList<Animator> items,long delay){
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(items);
        animatorSet.setStartDelay(delay);
        return animatorSet;
    }

}
