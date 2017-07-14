package com.xjk.android.presenter;

import android.widget.EditText;

import com.xjk.android.R;
import com.xjk.android.base.RxObserver;
import com.xjk.android.base.RxPresenter;
import com.xjk.android.bean.LoginBean;
import com.xjk.android.data.DataManager;
import com.xjk.android.presenter.contract.LoginContract;
import com.xjk.android.utils.RegexUtils;
import com.xjk.android.utils.UIUtils;

/**
 * Created by xxx on 2017/2/5.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter{

    @Override
    public void login(EditText etPhone, EditText etCode) {
        String phoneValue = etPhone.getText().toString().trim();
        String codeValue = etCode.getText().toString().trim();
        if (!RegexUtils.isMobileSimple(phoneValue)) {
            UIUtils.showToastLong(R.string.error_phone);
            return;
        }
        if (!RegexUtils.isMatch("^\\d{6}$", codeValue)) {
            UIUtils.showToastLong(R.string.error_code);
            return;
        }

        DataManager.getInstance().login(phoneValue,codeValue)
                .subscribe(new RxObserver<LoginBean>(mView) {
                    @Override
                    public void onNext(LoginBean value) {
                        super.onNext(value);
                        mView.loginSuccess(value);
                    }
                });
    }

//    @Override
//    public void bindDevice(final Dialog dialog, String ticket, String deviceNum) {
//        if (!RegexUtils.isMatch("^\\d{1}$", deviceNum)) {
//            UIUtils.showToastLong("设备号输入不正确");
//            return;
//        }
//
//        DataManager.getInstance().bindDevice(ticket,deviceNum)
//                .subscribe(new RxObserver<DeviceBean>(mRootView) {
//                    @Override
//                    public void onNext(DeviceBean value) {
//                        mRootView.bindDeviceSuccess(dialog,value);
//                    }
//                });
//    }

}
