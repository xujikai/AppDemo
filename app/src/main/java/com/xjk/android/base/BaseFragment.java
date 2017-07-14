package com.xjk.android.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xjk.android.R;
import com.xjk.android.utils.L;
import com.xjk.android.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xxx on 2016/12/27.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    protected String TAG = this.getClass().getSimpleName();

    protected T mPresenter;
    protected View mRootView;
    protected BaseActivity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        mActivity = (BaseActivity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        println("onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        println("onCreateView");
        if(mRootView == null){
            mRootView = inflater.inflate(getLayoutId(), container, false);
            initInject();
        }
        return mRootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initInject();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        println("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        if (mPresenter != null)
            mPresenter.attachView(this);
        mUnBinder = ButterKnife.bind(this, mRootView);
        initView();
        initListener();
        initData();
    }

    /**
     * 初始化View对象
     */
    protected void initView() {
    }

    /**
     * 初始化事件监听器
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onStart() {
        super.onStart();
        println("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        println("onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        println("onHiddenChanged：" + hidden + "，isHidden：" + isHidden());
    }

    @Override
    public void onPause() {
        super.onPause();
        println("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        println("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        println("onDestroyView");
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        println("onDestroy");
        if (mPresenter != null) mPresenter.detachView();
    }

    protected void println(String content) {
        L.e(TAG, content);
    }

    @Override
    public void showLoading(String msg) {
        mActivity.showLoading(msg);
    }

    @Override
    public void hideLoading() {
        mActivity.hideLoading();
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
        return isDetached();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return mActivity;
    }

}
