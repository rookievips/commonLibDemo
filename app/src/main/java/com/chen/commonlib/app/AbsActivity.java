package com.chen.commonlib.app;

import com.chen.api.base.BaseActivity;
import com.chen.api.base.BasePresenter;
import com.chen.commonlib.app.option.Constant;
import com.chen.commonlib.app.option.ResponseEntity;
import com.chen.commonlib.app.option.volley.HttpRequest;
import com.chen.commonlib.app.option.volley.OnRequestListener;
import com.chen.commonlib.app.option.volley.RequestManager;

import butterknife.ButterKnife;

/**
 * Author: Chen
 * Date: 2020/5/9 16:44
 * Des:
 */
public abstract class AbsActivity<P extends BasePresenter> extends BaseActivity<P> {


    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    protected <T> void request(Object data, boolean needLoading, OnRequestListener<T> onRequestListener) {
        request(data, getActVolleyTag(), needLoading, onRequestListener);
    }

    /**
     * 封装的网络请求
     *
     * @param data
     * @param needLoading
     * @param onRequestListener
     * @param <T>
     */
    public <T> void request(Object data, String tag, boolean needLoading, OnRequestListener<T> onRequestListener) {
//        String baseUrl = Constant.HOST + "/api/supply/cgi";
//        String baseUrl = Constant.HOST + "/purchase/cgi?token=664eee9d8b4a4934bbe85ec1da423d23";
        String baseUrl = Constant.HOST + "/api/purchase/cgi";

//        if (data instanceof RequestEntity) {
//            String cmd = ((RequestEntity) data).getCmd();
//            if (UserHelper.getToken() != null && !cmd.equals(Cmd.CMD_LOGIN)) {
//
//            }
//        }

        HttpRequest httpRequest = new HttpRequest<>(data, baseUrl, new OnRequestListener<T>() {
            @Override
            public T jsonToObj(String responseStr) {
                if (onRequestListener != null)
                    return onRequestListener.jsonToObj(responseStr);
                return null;
            }

            @Override
            public void onFail(int failCode, String msg) {
                if (onRequestListener != null) {
                    hideLoading();
                    onRequestListener.onFail(failCode, msg);
                }
            }

            @Override
            public void onResponseError(int failCode, String msg) {
                if (onRequestListener != null) {
                    hideLoading();
                    onRequestListener.onResponseError(failCode, msg);
                }
            }

            @Override
            public void onStart() {
                if (needLoading) {
                    showLoading();
                }
                if (onRequestListener != null) {
                    onRequestListener.onStart();
                }
            }

            @Override
            public void onResponse(ResponseEntity<T> responseEntity) {
                if (onRequestListener != null) {
                    hideLoading();
                    onRequestListener.onResponse(responseEntity);
                }
            }
        });

        RequestManager.getInstance().request(tag, httpRequest);
    }

    @Override
    public void invalidToken() {
        // TODO: 2019/8/1 跳转登录页面
    }

    /**
     * 获取http请求tag
     *
     * @return
     */
    private String getActVolleyTag() {
        return getClass().getName();
    }

    @Override
    protected void onDestroy() {
        RequestManager.getInstance().cancel(getActVolleyTag());
        super.onDestroy();
    }
}
