package com.chen.api.widgets;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Author: Chen
 * Date: 2018/6/6
 * Desc: 倒计时工具
 */
public class TimeCount extends CountDownTimer {
    private Button button;

    /**
     * @param millisInFuture    总时长 s
     * @param countDownInterval 倒计时的时间间隔 s
     * @param button          要显示的view
     */
    public TimeCount(int millisInFuture, int countDownInterval, Button button) {
        super(millisInFuture * 1000 + 2000, countDownInterval * 1000);
        this.button = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String time = "(" + (millisUntilFinished - 1000) / 1000 + ")s";
        setTextViewInfo(time, "#ffffff", false);
    }

    @Override
    public void onFinish() {
        setTextViewInfo("重新获取", "#ffffff", true);
    }

    /**
     * @param content   内容
     * @param color     颜色
     * @param clickable 是否可以点击
     */
    private void setTextViewInfo(String content, String color, boolean clickable) {
        button.setText(content);
        button.setTextColor(Color.parseColor(color));
        button.setClickable(clickable);
    }
}
