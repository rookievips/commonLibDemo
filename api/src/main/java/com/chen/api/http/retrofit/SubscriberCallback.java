package com.chen.api.http.retrofit;

import java.net.SocketTimeoutException;

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
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 403) {
                msg = HttpMsgConfig.FORBIDDEN;
            }
            if (code == 404) {
                msg = HttpMsgConfig.NOT_FOUND;
            }
            if (code == 408) {
                msg = HttpMsgConfig.REQUEST_TIMEOUT;
            }
            if (code == 500) {
                msg = HttpMsgConfig.INTERNAL_SERVER_ERROR;
            }
            if (code == 504) {
                msg = HttpMsgConfig.NO_INTERNET;
            }
            httpCallback.onFailed(code, msg);
        } else if (e instanceof SocketTimeoutException) {
            httpCallback.onFailed(408, HttpMsgConfig.REQUEST_TIMEOUT);
        }
    }

    @Override
    public void onNext(T responseEntity) {
        httpCallback.onSuccess(responseEntity);
    }

}
