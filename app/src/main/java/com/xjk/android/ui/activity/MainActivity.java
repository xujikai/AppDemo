package com.xjk.android.ui.activity;

import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;
import com.xjk.android.base.RxObserver;
import com.xjk.android.data.DataManager;
import com.xjk.android.ui.image_handle.HandleImage02Activity;
import com.xjk.android.ui.shadow_view.ShadowCardDragActivity;
import com.xjk.android.ui.web_view.WebViewActivity;
import com.xjk.android.utils.L;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_get_12306)
    public void get12306(){
        DataManager.getInstance().getTest()
                .subscribe(new RxObserver<String>(getBaseActivity()) {
                    @Override
                    public void onNext(String bean) {
                        super.onNext(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        L.e(e.getMessage());
                    }
                });
    }

    @OnClick(R.id.btn_image_large)
    public void getLargeImage(){
        HandleImage02Activity.start(mContext);
    }

    @OnClick(R.id.btn_shadow_card_stack)
    public void getShadowCardStack(){
        ShadowCardDragActivity.start(mContext);
    }

    @OnClick(R.id.btn_web_view)
    public void getWebView(){
        WebViewActivity.start(mContext, "https://www.baidu.com");
    }

}
