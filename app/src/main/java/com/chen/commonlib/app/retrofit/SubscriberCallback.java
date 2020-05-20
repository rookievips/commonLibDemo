package com.chen.commonlib.app.retrofit;

import android.text.TextUtils;

import com.chen.api.http.ResponseEntity;
import com.chen.api.http.ResponseError;
import com.chen.api.utils.ToastUtil;

import retrofit2.HttpException;
import rx.Subscriber;

public class SubscriberCallback<T> extends Subscriber<T> {

    private HttpCallback<T> httpCallback;

    public SubscriberCallback(HttpCallback<T> httpCallback) {
        this.httpCallback = httpCallback;
    }

    @Override
    public void onCompleted() {
        httpCallback.onComplete();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int failCode = httpException.code();
            String msg = httpException.getMessage();
            if (failCode == 403) {
                msg = HttpMsgConfig.FORBIDDEN;
            }
            if (failCode == 404) {
                msg = HttpMsgConfig.NOT_FOUND;
            }
            if (failCode == 408) {
                msg = HttpMsgConfig.REQUEST_TIMEOUT;
            }
            if (failCode == 500) {
                msg = HttpMsgConfig.INTERNAL_SERVER_ERROR;
            }
            if (failCode == 504) {
                msg = HttpMsgConfig.NO_INTERNET;
            }
            ToastUtil.showShort(failCode + ":" + msg);
            httpCallback.onFailed(failCode, msg);
        }
    }

    @Override
    public void onNext(T responseEntity) {
        if (responseEntity instanceof ResponseEntity) {
            ResponseError error = ((ResponseEntity) responseEntity).getError();
            if (error != null) {
                int errorCode = error.getErrorCode();
                String errorInfo = error.getErrorInfo();
                if (TextUtils.isEmpty(errorInfo)) {
                    errorInfo = "No errorInfo";
                }
                if (errorCode == 403) {
                    httpCallback.onInvalidToken();
                }else {
                    ToastUtil.showShort(errorCode + ":" + errorInfo);
                }
                httpCallback.onResponseError(errorCode,errorInfo);
            }
        }
        httpCallback.onResponse(responseEntity);
    }

}
