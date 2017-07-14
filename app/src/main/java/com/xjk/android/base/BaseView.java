package com.xjk.android.base;

/**
 * Created by xxx on 2016/9/27.
 */

public interface BaseView {

    /**
     * 显示加载进度条
     */
    void showLoading(String msg);

    /**
     * 隐藏加载进度条
     */
    void hideLoading();

    /**
     * 显示错误信息
     */
    void showError(String msg);

    /**
     * 显示无网络
     */
    void showNoNet();

    /**
     * 当前页面是否关闭
     */
    boolean isFinished();

    /**
     * 获取上下文
     */
    BaseActivity getBaseActivity();

}
