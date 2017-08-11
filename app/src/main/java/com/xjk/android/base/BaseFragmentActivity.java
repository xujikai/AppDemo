package com.xjk.android.base;

import android.support.v4.app.FragmentTransaction;

/**
 * 动态创建Fragment页面
 *
 * 注意事项：
 *  1. 初始化mFragments
 *  2. 实现createFragment方法
 *
 * Created by xxx on 2017/6/5.
 */

public abstract class BaseFragmentActivity<T> extends BaseActivity{

    protected BaseFragment[] mFragments;

    /**
     * 切换Fragment
     */
    public void switchFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BaseFragment fragment = createFragment(position, transaction);
        hideFragment(transaction);
        transaction.show(fragment);
        transaction.commit();
    }

    /**
     * 创建Fragment
     */
    protected abstract BaseFragment createFragment(int position, FragmentTransaction transaction);

    /**
     * 隐藏所有的fragment
     */
    protected void hideFragment(FragmentTransaction transaction) {
        for (BaseFragment fragment : mFragments) {
            if (fragment != null) transaction.hide(fragment);
        }
    }

}
