package com.chen.api.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.chen.api.widgets.MsgView;

/**
 * 未读消息提示View,显示小红点或者带有数字的红点:
 * 数字一位,圆
 * 数字两位,圆角矩形,圆角是高度的一半
 * 数字超过两位,显示99+
 */
public class UnreadMsgUtils {
    public static void showDot(MsgView msgView, boolean isShow) {
        showMsg(msgView, 0, isShow, true);
    }

    public static void showMsg(MsgView msgView, int num, boolean isShow, boolean isLimit) {
        if (msgView == null) {
            return;
        }
        if (!isShow) {
            msgView.setVisibility(View.GONE);
            return;
        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) msgView.getLayoutParams();
        DisplayMetrics dm = msgView.getResources().getDisplayMetrics();
        msgView.setVisibility(View.VISIBLE);
        if (num <= 0) {//圆点,设置默认宽高
            msgView.setText("");

            lp.width = (int) (7 * dm.density);
            lp.height = (int) (7 * dm.density);
            msgView.setLayoutParams(lp);

        } else {
            lp.height = (int) (17 * dm.density);
            if (num > 0 && num < 10) {//圆
                lp.width = (int) (17 * dm.density);
                msgView.setText(num + "");
            } else if (num > 9 && num < 100) {//圆角矩形,圆角是高度的一半,设置默认padding
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                msgView.setPadding((int) (4 * dm.density), 0, (int) (4 * dm.density), 0);
                msgView.setText(num + "");
            } else if (isLimit) {//数字超过两位,显示99+
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                msgView.setPadding((int) (4 * dm.density), 0, (int) (4 * dm.density), 0);
                msgView.setText("99+");
            } else {
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                msgView.setPadding((int) (4 * dm.density), 0, (int) (4 * dm.density), 0);
                msgView.setText(num + "");

            }
            msgView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            msgView.setGravity(Gravity.CENTER);
            msgView.setLayoutParams(lp);
        }
    }
}
