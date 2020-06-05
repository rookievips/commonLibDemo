package com.chen.commonlib.app.retrofit;

import com.chen.api.helper.DisposableManager;
import com.chen.api.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class ObserverCallback<T> implements Observer<T> {
    private HttpCallback<T> httpCallback;

    public ObserverCallback(HttpCallback<T> httpCallback) {
        this.httpCallback = httpCallback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        DisposableManager.getInstance().add(d);
        httpCallback.onStart();
    }

    @Override
    public void onNext(T t) {
        httpCallback.onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        int code = HttpConstant.CODE_UN_KNOW_ERROR;
        String msg = HttpConstant.UN_KNOW_ERROR;

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            code = httpException.code();
            msg = httpException.getMessage();
            if (code == 403) {
                msg = HttpConstant.FORBIDDEN;
            }
            if (code == 404) {
                msg = HttpConstant.NOT_FOUND;
            }
            if (code == 408) {
                msg = HttpConstant.REQUEST_TIMEOUT;
            }
            if (code == 500) {
                msg = HttpConstant.INTERNAL_SERVER_ERROR;
            }
            if (code == 504) {
                msg = HttpConstant.GATEWAY_TIMEOUT;
            }

        } else if (e instanceof UnknownHostException) {
            code = HttpConstant.CODE_NO_INTERNET;
            msg = HttpConstant.NO_INTERNET;

        } else if (e instanceof ConnectException) {
            code = HttpConstant.CODE_CONNECT_FAIL;
            msg = HttpConstant.CONNECT_FAIL;

        } else if (e instanceof SocketTimeoutException) {
            code = HttpConstant.CODE_CONNECT_TIME_OUT;
            msg = HttpConstant.CONNECT_TIME_OUT;

        } else if (e instanceof TokenInvalidException) {
            TokenInvalidException tokenInvalidException = (TokenInvalidException) e;
            code = tokenInvalidException.getCode();
            msg = tokenInvalidException.getMsg();

        } else if (e instanceof ServerException) {
            //后台服务器返回的error信息
            ServerException serverException = (ServerException) e;
            code = serverException.getCode();
            msg = serverException.getMsg();

        }
        ToastUtil.showShort(msg);
        httpCallback.onFail(code, msg);
    }

    @Override
    public void onComplete() {
        httpCallback.onComplete();
    }
}
