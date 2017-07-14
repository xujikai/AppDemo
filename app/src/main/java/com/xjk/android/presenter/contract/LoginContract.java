package com.xjk.android.presenter.contract;

import android.widget.EditText;

import com.xjk.android.base.BasePresenter;
import com.xjk.android.base.BaseView;
import com.xjk.android.bean.LoginBean;

/**
 * 登录界面
 * Created by xxx on 2016/10/31.
 */

public interface LoginContract {

    interface View extends BaseView {

        /**
         * 登录成功
         */
        void loginSuccess(LoginBean bean);

//        void bindDeviceSuccess(Dialog dialog, DeviceBean bean);

    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 登录
         * @param etPhone   手机号
         * @param etCode    验证码
         */
        void login(EditText etPhone, EditText etCode);

//        void bindDevice(Dialog dialog, String ticket, String deviceNum);

    }


}
