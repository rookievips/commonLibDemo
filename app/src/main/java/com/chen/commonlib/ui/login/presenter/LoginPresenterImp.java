package com.chen.commonlib.ui.login.presenter;


import com.chen.commonlib.app.option.RequestEntity;
import com.chen.commonlib.app.option.ResponseEntity;
import com.chen.commonlib.app.option.retrofit.Http;
import com.chen.api.http.retrofit.HttpCallback;
import com.chen.api.http.retrofit.SubscriberCallback;
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
            public void onSuccess(ResponseEntity<Map<String, Object>> responseEntity) {
                if (isSuccess(responseEntity.getStatusCode())) {
                    if (responseEntity.getResponse() != null) {
                        ((LoginContact.LoginView)baseView).loginSuccess(responseEntity.getResponse());
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
