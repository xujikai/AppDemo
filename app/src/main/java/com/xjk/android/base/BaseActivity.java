package com.xjk.android.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.xjk.android.R;
import com.xjk.android.utils.L;
import com.xjk.android.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xxx on 2016/12/27.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected String TAG = this.getClass().getSimpleName();
    protected Activity mContext;
    protected View mRootView;
    protected ProgressDialog mDialog;

    protected T mPresenter;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        println("onCreate");
        mRootView = LayoutInflater.from(this).inflate(getLayout(), null);
        setContentView(mRootView);

        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        AppManager.getAppManager().addActivity(this);
        initIntent(savedInstanceState);
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        initView();
        initListener();
        initData();
    }

    /**
     * 获取Intent内的值
     */
    protected void initIntent(Bundle savedInstanceState) {}

    /**
     * 初始化布局
     */
    protected abstract int getLayout();

    /**
     * 初始化注入对象
     */
    protected abstract void initInject();

    /**
     * 初始化View对象
     */
    protected void initView() {}

    /**
     * 初始化事件监听器
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onStart() {
        super.onStart();
        println("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        println("onResume");
        if (mPresenter != null)
            mPresenter.resume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        println("onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        println("onPause");
        if (mPresenter != null)
            mPresenter.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        println("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        println("onDestroy");
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        AppManager.getAppManager().removeActivity(this);
    }

    /**
     * 测试打印方法
     */
    protected void println(String content) {
        L.e(TAG, content);
    }

    /**
     * 屏幕变亮
     */
    protected void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    /**
     * 屏幕变暗
     */
    protected void lightOff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    /**
     * 显示软键盘
     */
    protected void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 判断软键盘是否显示(暂未实验)
     */
    protected boolean isShowSoftInput() {
        return getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
    }

    /**
     * 如果传null，对话框不会显示
     */
    @Override
    public void showLoading(String msg) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
        }
        if (msg == null) return;
        mDialog.setMessage(msg);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        /** 防止页面销毁后弹出加载框，造成崩溃 */
        if (!isFinishing()) mDialog.show();
    }

    /**
     * 隐藏加载框
     */
    @Override
    public void hideLoading() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    /**
     * 通用网络请求加载错误回调方法
     */
    @Override
    public void showError(String msg) {
        UIUtils.showToastLong(msg);
    }

    @Override
    public void showNoNet() {
        UIUtils.showToastLong(R.string.api_net_disable);
    }

    @Override
    public boolean isFinished() {
        return isFinishing();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

}
