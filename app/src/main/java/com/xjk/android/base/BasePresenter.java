package com.xjk.android.base;

/**
 * Created by xxx on 2016/9/27.
 */

public interface BasePresenter<T extends BaseView> {

    /**
     * 在Activity或Fragment中的onCreate()中调用
     */
    void attachView(T view);

    /**
     * 在Activity或Fragment中的onDestroy()中调用
     */
    void detachView();

    /**
     * 在Activity或Fragment中的onResume()中调用
     */
    void resume();

    /**
     * 在Activity或Fragment中的onPause()中调用
     */
    void pause();

}
