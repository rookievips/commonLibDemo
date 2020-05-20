package com.chen.api.base;


import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.chen.api.R;

public abstract class BaseWebActivity extends BaseActivity {
    private WebView wv;
    private ProgressBar progress;


    @Override
    protected int layoutResId() {
        return R.layout.activity_base_web;
    }

    @Override
    protected void initButterKnife() {}

    @Override
    protected void initView() {
        wv = findViewById(R.id.wv);
        progress = findViewById(R.id.progress);
        initWeb();
    }

    @Override
    protected void afterCreated() {
        loadUrl(initialUrl());
    }

    /**
     * 加载网页
     *
     * @param url
     */
    private void loadUrl(String url) {
        if (TextUtils.isEmpty(url))
            return;
        wv.loadUrl(url);
        Log.i("h5",url);
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @SuppressWarnings("deprecation")
    private void initWeb() {
        wv.setScrollContainer(false);//设置滑动容器
        wv.setVerticalScrollBarEnabled(false);//隐藏垂直滚动条
        wv.setSoundEffectsEnabled(true);
        wv.playSoundEffect(WebView.SOUND_EFFECTS_ENABLED);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//让JavaScript自动打开窗口，默认false
        webSettings.setAllowFileAccess(true);//是否允许访问文件
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//可能的话使所有列的宽度不超过屏幕宽度
        webSettings.setSupportZoom(false);//缩放
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);//WebView是否支持HTML的“viewport”标签或者使用wide viewport
        webSettings.setSupportMultipleWindows(true);
        webSettings.setLoadWithOverviewMode(true);//使页面充满webView
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染的优先级
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setDomStorageEnabled(true);//设置DOM Storage缓存
        webSettings.setGeolocationEnabled(true);//启用地理位置
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//https 页面里面有http形式的图片地址
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        wv.addJavascriptInterface(initJsBridge(),"JsBridge");
        wv.setWebViewClient(initWebViewClient());
        wv.setWebChromeClient(initWebChromeClient());
    }

    private WebChromeClient initWebChromeClient() {
        return new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 99) {
                    progress.setVisibility(View.GONE);
                } else {
                    if (!progressVisible()) {
                        progress.setVisibility(View.VISIBLE);
                    }
                    progress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                receivedTitle(view, title);
            }
        };
    }

    protected abstract Object initJsBridge();

    protected abstract WebViewClient initWebViewClient();

    protected abstract void receivedTitle(WebView view, String title);

    protected abstract String initialUrl();

    /**
     * progressBar是否可见
     *
     * @return
     */
    private boolean progressVisible() {
        return progress.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void backClick() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (wv != null) {
            wv.destroy();
        }
        super.onDestroy();
    }
}
