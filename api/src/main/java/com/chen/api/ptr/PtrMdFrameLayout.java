package com.chen.api.ptr;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.chen.api.R;
import com.chen.api.ptr.header.MaterialHeader;
import com.chen.api.utils.DisplayUtil;

public class PtrMdFrameLayout extends PtrFrameLayout {

    protected MaterialHeader mPtrClassicHeader;

    protected int paddingTop;
    protected int paddingBottom;

    public PtrMdFrameLayout(Context context) {
        super(context);
        initViews(context,null);
    }

    public PtrMdFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context,attrs);
    }

    public PtrMdFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context,attrs);
    }

    int sort(int[] arrays){
        int flag = arrays[0];
        for(int i=1;i<arrays.length;i++){
            if(flag>arrays[i]){
                flag = arrays[i];
            }
        }
        return flag;
    }

    private void initViews(Context context, AttributeSet attrs) {

        initBefore();
        if (attrs != null) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.PtrFrameLayout, 0, 0);
            if (arr != null) {

                paddingTop = arr.getDimensionPixelSize(R.styleable.PtrFrameLayout_ptr_padding_top,paddingTop);
                paddingBottom = arr.getDimensionPixelSize(R.styleable.PtrFrameLayout_ptr_padding_bottom, paddingBottom);
                arr.recycle();
            }
        }

        mPtrClassicHeader = new MaterialHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        mPtrClassicHeader.setColorSchemeColors(colors);
        mPtrClassicHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        mPtrClassicHeader.setPadding(0, paddingTop, 0, paddingBottom);
        mPtrClassicHeader.setPtrFrameLayout(this);

        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
    }

    protected void initBefore(){
        paddingTop =  DisplayUtil.dp2px(15);
        paddingBottom = DisplayUtil.dp2px(15);
    }

    public MaterialHeader getHeader() {
        return mPtrClassicHeader;
    }


    @Override
    public void setVisibility(int visibility) {
        if (mContent != null){
            mContent.setVisibility(visibility);
        }
    }
}
