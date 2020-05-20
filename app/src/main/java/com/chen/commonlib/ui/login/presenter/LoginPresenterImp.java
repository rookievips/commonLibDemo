package com.chen.commonlib.ui.login.presenter;


import com.chen.api.http.RequestEntity;
import com.chen.api.http.ResponseEntity;
import com.chen.commonlib.app.retrofit.Http;
import com.chen.commonlib.app.retrofit.HttpCallback;
import com.chen.commonlib.app.retrofit.SubscriberCallback;
import com.chen.commonlib.ui.login.contact.LoginContact;

import java.util.Map;

/**
 * Author: Chen
 * Date: 2018/8/16
 * Desc:
 */
public class LoginPresenterImp extends LoginContact.Presenter {
    @Override
    public void login(RequestEntity requestEntity) {
        addSubscribe(Http.getDefault().login(requestEntity),new SubscriberCallback<>(new HttpCallback<ResponseEntity<Map<String,Object>>>() {

            @Override
            public void onResponse(ResponseEntity<Map<String, Object>> responseEntity) {
                if (isSuccess(responseEntity.getStatusCode())) {
                    if (responseEntity.getResponse() != null) {
                        ((LoginContact.LoginView)baseView).loginSuccess(responseEntity.getResponse());
                    }
                }
            }

            @Override
            public void onResponseError(int errorCode, String msg) {

            }

            @Override
            public void onInvalidToken() {
                baseView.invalidToken();
            }

            @Override
            public void onFailed(int failCode, String msg) {
                baseView.hideLoading();
            }

            @Override
            public void onComplete() {
                baseView.hideLoading();
            }
        }));
    }
}
