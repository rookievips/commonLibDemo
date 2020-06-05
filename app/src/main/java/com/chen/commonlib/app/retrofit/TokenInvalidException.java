package com.chen.commonlib.app.retrofit;

import android.content.Intent;

import com.chen.api.helper.ActManager;
import com.chen.commonlib.ui.login.view.LoginActivity;

public class TokenInvalidException extends RuntimeException {
    private int code;
    private String msg;

    public TokenInvalidException(int code,String msg) {
        super();
        this.code = code;
        this.msg = msg;
        ActManager.getInstance().getCurrentActivity().startActivity(new Intent(ActManager.getInstance().getCurrentActivity().getApplicationContext(),LoginActivity.class));
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
