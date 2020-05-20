package com.chen.commonlib.h5;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chen.api.base.BaseWebActivity;
import com.chen.commonlib.app.Constant;

public class CommonWebActivity extends BaseWebActivity {
    private String webUrl;
    private boolean needTitleBar;

    @Override
    protected void initView() {
        super.initView();
        webUrl = getIntent().getStringExtra(Constant.KEY_WEB_URL);
        String webTitle = getIntent().getStringExtra(Constant.KEY_WEB_TITLE);
        if (needTitleBar) {
            commonTitleBarStyle(webTitle);
        }
    }

    @Override
    protected View initTitleBar() {
        needTitleBar = getIntent().getBooleanExtra(Constant.KEY_WEB_NEED_TITLE, true);
        return needTitleBar ? super.initTitleBar() : null;
    }

    @Override
    protected Object initJsBridge() {
        return null;
    }

    @Override
    protected WebViewClient initWebViewClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO: 2020/5/19 可以在此处理交互逻辑...H5调Android
                return super.shouldOverrideUrlLoading(view, url);
            }
        };
    }

    @Override
    protected void receivedTitle(WebView view, String title) {

    }

    @Override
    protected String initialUrl() {
        return webUrl;
    }
}
