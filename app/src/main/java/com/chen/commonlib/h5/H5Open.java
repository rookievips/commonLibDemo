package com.chen.commonlib.h5;

import android.app.Activity;
import android.content.Intent;

import com.chen.commonlib.app.option.Constant;


public class H5Open {

    public static void open(Activity activity, boolean needTitle, String webUrl, String webTitle) {
        Intent intent = new Intent(activity, CommonWebActivity.class);
        intent.putExtra(Constant.KEY_WEB_URL, webUrl);
        if (needTitle) {
            intent.putExtra(Constant.KEY_WEB_TITLE, webTitle);
        } else {
            intent.putExtra(Constant.KEY_WEB_NEED_TITLE, false);
        }
        activity.startActivity(intent);
    }

    public static void open(Activity activity, boolean needTitle, String webUrl, String webTitle, int requestCode) {
        Intent intent = new Intent(activity, CommonWebActivity.class);
        intent.putExtra(Constant.KEY_WEB_URL, webUrl);
        if (needTitle) {
            intent.putExtra(Constant.KEY_WEB_TITLE, webTitle);
        } else {
            intent.putExtra(Constant.KEY_WEB_NEED_TITLE, false);
        }
        activity.startActivityForResult(intent, requestCode);
    }
}
