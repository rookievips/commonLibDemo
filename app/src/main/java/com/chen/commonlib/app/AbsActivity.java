package com.chen.commonlib.app;

import com.chen.api.base.BaseActivity;
import com.chen.api.utils.ToastUtil;
import com.chen.api.http.ResponseEntity;
import com.chen.api.http.HttpRequest;
import com.chen.api.http.OnRequestListener;
import com.chen.api.http.RequestManager;

import butterknife.ButterKnife;


public abstract class AbsActivity extends BaseActivity {


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
//        String baseUrl = Constant.HOST + "/purchase/cgi?token=30e7f338e6994ad49c96f6f008113b4b";
        String baseUrl = Constant.HOST + "/api/purchase";

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
                showErrorMsg(failCode + ":" + msg);
                if (onRequestListener != null) {
                    hideLoading();
                    onRequestListener.onFail(failCode, msg);
                }
            }

            @Override
            public void onResponseError(int errorCode, String msg) {
                if (errorCode == 403) {
                    invalidToken();
                }else {
                    showErrorMsg(errorCode + ":" + msg);
                }
                if (onRequestListener != null) {
                    hideLoading();
                    onRequestListener.onResponseError(errorCode, msg);
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

    public void invalidToken() {
        // TODO: 2019/8/1 跳转登录页面
        ToastUtil.showShort("跳转登录界面");
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
