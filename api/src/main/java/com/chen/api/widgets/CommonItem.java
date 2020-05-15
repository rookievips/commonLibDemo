package com.chen.api.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.api.R;


/**
 * Author: Chen
 * Date: 2018/7/13
 * Desc:
 */
public class CommonItem extends RelativeLayout {
    private TextView tvTitle;
    private EditText edInput;
    private TextView tvInput;
    private ImageView ivArrow;
    private View bottomLine;

    private boolean type_edit;
    private String title_text;
    private int title_text_size;
    private int title_text_color;
    private boolean title_mark;

    private int input_padding_left;
    private int input_padding_top;
    private int input_padding_right;
    private int input_padding_bottom;
    private String input_text;
    private int input_text_color;
    private int input_text_size;
    private int input_max_length;
    private boolean tv_input_text_single;
    private boolean tv_input_ellipsize_end;
    private String ed_input_text_hint;
    private boolean ed_input_cleanable;

    private Drawable iv_arrow_src;
    private boolean iv_arrow_show;

    private boolean bottom_line_visible;
    private int input_gravity;

    public CommonItem(Context context) {
        this(context, null);
    }

    public CommonItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonItem);

        type_edit = typedArray.getBoolean(R.styleable.CommonItem_type_edit, false);
        title_text = typedArray.getString(R.styleable.CommonItem_title_text);
        title_text_size = typedArray.getDimensionPixelSize(R.styleable.CommonItem_title_text_size, -1);
        title_text_color = typedArray.getColor(R.styleable.CommonItem_title_text_color, Color.parseColor("#434343"));
        title_mark = typedArray.getBoolean(R.styleable.CommonItem_title_mark, false);

        input_padding_left = typedArray.getDimensionPixelOffset(R.styleable.CommonItem_input_padding_left, -1);
        input_padding_top = typedArray.getDimensionPixelOffset(R.styleable.CommonItem_input_padding_top, -1);
        input_padding_right = typedArray.getDimensionPixelOffset(R.styleable.CommonItem_input_padding_right, -1);
        input_padding_bottom = typedArray.getDimensionPixelOffset(R.styleable.CommonItem_input_padding_bottom, -1);
        input_text = typedArray.getString(R.styleable.CommonItem_input_text);
        input_text_color = typedArray.getColor(R.styleable.CommonItem_input_text_color, Color.parseColor("#434343"));
        input_text_size = typedArray.getDimensionPixelSize(R.styleable.CommonItem_input_text_size, -1);
        input_max_length = typedArray.getInt(R.styleable.CommonItem_input_max_length, -1);
        tv_input_text_single = typedArray.getBoolean(R.styleable.CommonItem_tv_input_text_single, true);
        tv_input_ellipsize_end = typedArray.getBoolean(R.styleable.CommonItem_tv_input_ellipsize_end, true);
        ed_input_text_hint = typedArray.getString(R.styleable.CommonItem_ed_input_text_hint);
        ed_input_cleanable = typedArray.getBoolean(R.styleable.CommonItem_ed_input_cleanable, false);

        iv_arrow_src = typedArray.getDrawable(R.styleable.CommonItem_iv_arrow_src);
        iv_arrow_show = typedArray.getBoolean(R.styleable.CommonItem_iv_arrow_show, true);

        bottom_line_visible = typedArray.getBoolean(R.styleable.CommonItem_bottom_line_visible, true);
        input_gravity = typedArray.getInt(R.styleable.CommonItem_input_gravity, -1);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View contentView;
        if (type_edit) {
            contentView = View.inflate(getContext(), R.layout.common_input_item, null);
            edInput = contentView.findViewById(R.id.ed_input);

            setInputTextLayoutPadding(edInput);
            edInput.setText(input_text);
            edInput.setTextColor(input_text_color);
            edInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, input_text_size);
            if (input_max_length > -1) {
                edInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(input_max_length)});
            }
            if (input_gravity > -1) {
                edInput.setGravity(input_gravity);
            }

            edInput.setHint(ed_input_text_hint);
        } else {
            contentView = View.inflate(getContext(), R.layout.common_text_item, null);
            tvInput = contentView.findViewById(R.id.tv_input);

            setInputTextLayoutPadding(tvInput);
            tvInput.setText(input_text);
            tvInput.setTextColor(input_text_color);
            tvInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, input_text_size);
            if (input_max_length > -1) {
                tvInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(input_max_length)});
            }
            if (input_gravity > -1) {
                tvInput.setGravity(input_gravity);
            }

            if (tv_input_text_single) {
                tvInput.setSingleLine();
            }

            if (tv_input_ellipsize_end) {
                tvInput.setSingleLine();
                tvInput.setEllipsize(TextUtils.TruncateAt.END);
            }
        }

        tvTitle = contentView.findViewById(R.id.tv_title);
        ivArrow = contentView.findViewById(R.id.iv_arrow);
        bottomLine = contentView.findViewById(R.id.bottom_line);

        tvTitle.setText(title_text);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, title_text_size);
        if (title_text_color != 0) {
            tvTitle.setTextColor(title_text_color);
        }
        if (title_mark) {
            title_text = title_text == null ? "" : title_text;
            String s = title_text + "<font color=\"#ff0000\"> *</font>";
            tvTitle.setText(Html.fromHtml(s));
        } else {
            tvTitle.setText(title_text);
        }

        if (iv_arrow_show) {
            ivArrow.setVisibility(VISIBLE);
        } else {
            ivArrow.setVisibility(GONE);
        }

        if (bottom_line_visible) {
            bottomLine.setVisibility(VISIBLE);
        } else {
            bottomLine.setVisibility(INVISIBLE);
        }

        if (iv_arrow_src != null) {
            ivArrow.setImageDrawable(iv_arrow_src);
        }

        if (type_edit && ed_input_cleanable) {
            updateCleanable();
            edInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    updateCleanable();
                }
            });
            ivArrow.setOnClickListener(v -> {
                if (ivArrow.getVisibility() == View.VISIBLE) {
                    edInput.setText("");
                }
            });
        }
        addView(contentView);
    }

    /**
     * 更新清除状态
     */
    private void updateCleanable() {
        if (edInput != null && edInput.length() > 0) {
            ivArrow.setVisibility(VISIBLE);
            ivArrow.setClickable(true);
        } else if (edInput != null) {
            ivArrow.setVisibility(INVISIBLE);
            ivArrow.setClickable(false);
        }
    }

    /**
     * 设置内间距
     * @param input
     */
    private void setInputTextLayoutPadding(TextView input) {
        int paddingLeft = input.getPaddingLeft();
        int paddingTop = input.getPaddingTop();
        int paddingRight = input.getPaddingRight();
        int paddingBottom = input.getPaddingBottom();
        if (input_padding_left > 0) {
            paddingLeft = input_padding_left;
        }
        if (input_padding_top > 0) {
            paddingTop = input_padding_top;
        }
        if (input_padding_right > 0) {
            paddingRight = input_padding_right;
        }
        if (input_padding_bottom > 0) {
            paddingBottom = input_padding_bottom;
        }
        input.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public String getInput_text() {
        return input_text;
    }

    public void setInput_text(String input_text) {
        this.input_text = input_text;

        if (edInput != null) {
            edInput.setText(input_text);
        } else {
            tvInput.setText(input_text);
        }
    }

    public String getTitle_text() {
        return title_text;
    }

    public void setTitle_text(String title_text) {
        this.title_text = title_text;
        tvTitle.setText(title_text);
    }
}
