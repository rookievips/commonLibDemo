package com.chen.commonlib.ui.login.contact;


import com.chen.api.base.BasePresenter;
import com.chen.api.base.BaseView;
import com.chen.commonlib.app.option.RequestEntity;

import java.util.Map;


public class LoginContact {

    public interface LoginView extends BaseView {
        void loginSuccess(Map<String, Object> loginEntity);
    }

    //...


    public static abstract class Presenter extends BasePresenter<BaseView> {
        public abstract void login(RequestEntity requestEntity);

        //....
    }

}
