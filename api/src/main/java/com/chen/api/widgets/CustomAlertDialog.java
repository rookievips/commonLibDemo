package com.chen.api.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chen.api.R;


public class CustomAlertDialog extends Dialog {

    private TextView title, message;
    private Button yes, no;
    private CharSequence customTitle, customMessage, customYes, customNo; //自定义的提示标题,提示消息,确定按钮
    private View line_ver;

    private YesOnClickListener yesOnClickListener; //确定按钮被点击了的监听器
    private NoOnClickListener noOnClickListener; //取消按钮被点击了的监听器

    public CustomAlertDialog(Context context) {
        super(context, R.style.CustomAlertDialog);
        setContentView(R.layout.custom_alert_dialog);
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
        initData();
    }

    /**
     * 初始化界面控件要显示的数据
     */
    private void initData() {
        if (customTitle != null) {
            title.setText(customTitle);
        }

        if (customMessage != null) {
            message.setText(customMessage);
        }

        if (customYes != null) {
            yes.setText(customYes);
        }

        if (customNo != null) {
            no.setText(customNo);
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        title = findViewById(R.id.custom_dialog_title);
        message = findViewById(R.id.custom_dialog_message);
        yes = findViewById(R.id.custom_dialog_yes);
        no = findViewById(R.id.custom_dialog_no);
        line_ver = findViewById(R.id.line_ver);
    }

    /**
     * 初始化按钮的点击事件(确定,取消...)
     */
    private void initEvent() {
        yes.setOnClickListener(v -> yesOnClickListener.yesClick());
        no.setOnClickListener(v -> noOnClickListener.noClick());
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param strYes
     * @param yesOnClickListener
     */
    public CustomAlertDialog setYesOnClickListener(String strYes, YesOnClickListener yesOnClickListener) {
        if (strYes != null) {
            customYes = strYes;
        }
        this.yesOnClickListener = yesOnClickListener;
        return this;
    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param strNo
     * @param noOnClickListener
     */
    public CustomAlertDialog setNoOnClickListener(String strNo, NoOnClickListener noOnClickListener) {
        if (strNo != null) {
            customNo = strNo;
        }
        this.noOnClickListener = noOnClickListener;
        return this;
    }

    public interface YesOnClickListener {
        void yesClick();
    }

    public interface NoOnClickListener {
        void noClick();
    }

    /**
     * 从外界设置提示标题
     *
     * @param title
     */
    public CustomAlertDialog setTitles(CharSequence title) {
        customTitle = title;
        return this;
    }

    /**
     * 从外界设置提示内容
     *
     * @param message
     */
    public CustomAlertDialog setMessage(CharSequence message) {
        customMessage = message;
        return this;
    }

    /**
     * 从外界设置是否显示取消按钮
     *
     * @param noVis
     */
    public CustomAlertDialog setNoVis(boolean noVis) {
        if (no != null && line_ver != null) {
            if (noVis) {
                no.setVisibility(View.VISIBLE);
                line_ver.setVisibility(View.VISIBLE);
            } else {
                no.setVisibility(View.GONE);
                line_ver.setVisibility(View.GONE);
            }
        }
        return this;
    }

    /**
     * 提供外界设置是否显示title
     *
     * @param titleVis
     */
    public CustomAlertDialog setTitleVis(boolean titleVis) {
        if (title != null) {
            if (titleVis) {
                title.setVisibility(View.VISIBLE);
            } else {
                title.setVisibility(View.GONE);
            }
        }
        return this;
    }

    /**
     * 提供外界设置title的颜色
     *
     * @param strColor
     */
    public CustomAlertDialog setTitleColor(String strColor) {
        if (title != null) {
            title.setTextColor(Color.parseColor(strColor));
        }
        return this;
    }

    /**
     * 提供外界设置message的颜色
     *
     * @param strColor
     * @return
     */
    public CustomAlertDialog setMessageColor(String strColor) {
        if (message != null) {
            message.setTextColor(Color.parseColor(strColor));
        }
        return this;
    }

    /**
     * 设置点击屏幕外dialog是否可以消失
     *
     * @param canceled
     * @return
     */
    public CustomAlertDialog setCancelOnTouchOutside(boolean canceled) {
        setCanceledOnTouchOutside(canceled);
        return this;
    }
}
