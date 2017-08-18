package com.xjk.android.ui.web_view;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;
import com.xjk.android.utils.StringUtils;

import butterknife.BindView;

/**
 * Created by xxx on 2017/7/28.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static void start(Context context, String url) {
        if (StringUtils.isEmpty(url)) return;
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("web", url);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String web = intent.getStringExtra("web");
        webView.loadUrl(web);
        // 这个字符串android，为html调用本地方法需要使用的别名
        webView.addJavascriptInterface(new WebAppInterface(this), "android");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar == null) return;
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * 跳转至网红主页
         *
         * @param uid 网红id
         */
        @JavascriptInterface
        public void startGirlHomeActivity(int uid) {

        }

        /**
         * 分享活动
         *
         * @param url   分享地址
         * @param title 标题
         * @param thumb 缩略图
         * @param des   描述
         */
        @JavascriptInterface
        public void startActionShare(String url, String title, String thumb, String des) {

        }

        /**
         * 跳转到充值界面
         */
        @JavascriptInterface
        public void startPayActivity() {

        }
    }


}
