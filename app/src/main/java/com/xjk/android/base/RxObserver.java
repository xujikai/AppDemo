package com.xjk.android.base;

import com.xjk.android.R;
import com.xjk.android.data.api.ApiException;
import com.xjk.android.utils.NetworkUtils;
import com.xjk.android.utils.UIUtils;

import io.reactivex.observers.DisposableObserver;

/**
 * 订阅封装
 */
public abstract class RxObserver<T> extends DisposableObserver<T> {

    private BaseView mView;
    private String mMsg;
    private boolean isShowDialog;

    public RxObserver(BaseView view, String msg, boolean showDialog) {
        this.mView = view;
        this.mMsg = msg;
        this.isShowDialog = showDialog;
    }

    public RxObserver(BaseView view){
        this(view, UIUtils.getString(R.string.api_loading), true);
    }

    public RxObserver(BaseView view, boolean showDialog){
        this(view, UIUtils.getString(R.string.api_loading), showDialog);
    }

    @Override
    public void onStart() {
        if(isShowDialog) mView.showLoading(mMsg);
    }

    @Override
    public void onNext(T bean) {
        if(mView.isFinished()) return;
        if(isShowDialog) mView.hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        if(mView.isFinished()) return;
        if(isShowDialog) mView.hideLoading();
//        应对返回值有时是对象，有时是字符串的现象
//        if(e instanceof JsonSyntaxException)
//        应对返回值有时是对象，有时是null的现象
//        if(e instanceof NullPointerException)

        if(!NetworkUtils.isConnected()){
            mView.showNoNet();
        }else if(e instanceof ApiException){
//            ApiBean bean = ((ApiException) e).getBean();
//            if(bean.getStatus() == 400 || bean.getStatus() == 401){
//                RxBus.get().post(Constants.EventType.TAG_RELOGIN_ACTIVITY, true);
//            }
            mView.showError(e.getMessage());
        }else {
            mView.showError(UIUtils.getString(R.string.api_net_error));
        }
    }

    @Override
    public void onComplete() {}
}
