package com.chen.api.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Desc: 只能在最后输入数据的输入框
 */
public class LastCursorEditText extends AppCompatEditText {

    public LastCursorEditText(Context context) {
        super(context);
    }

    public LastCursorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LastCursorEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selStart == selEnd) { // 防止不能多选
            setSelection(getText().length()); // 保证光标始终在最后面
        }
    }
}
