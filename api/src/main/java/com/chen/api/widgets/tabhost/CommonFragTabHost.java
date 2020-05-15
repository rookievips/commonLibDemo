package com.chen.api.widgets.tabhost;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.api.R;
import com.chen.api.utils.UnreadMsgUtils;
import com.chen.api.widgets.MsgView;

import java.util.ArrayList;
import java.util.List;

public class CommonFragTabHost extends FrameLayout {
    private Context mContext;
    private int tabItemRes;
    private List<TabEntity> mTabEntities = new ArrayList<>();
    private LinearLayout mTabsContainer;
    private int mCurrentTab;
    private int mTabCount;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private FragmentChangeManager mFragmentChangeManager;

    /**
     * title
     */
    private float mTextSize;
    private int mTextSelectedColor;
    private int mTextUnselectedColor;
    private boolean mTextBold;
    private boolean mTextAllCaps;

    /**
     * icon
     */
    private boolean mIconVisible;
    private int mIconGravity;
    private float mIconWidth;
    private float mIconHeight;
    private float mIconMargin;

    /**
     * underline
     */
    private int mUnderlineColor;
    private float mUnderlineHeight;
    private int mUnderlineGravity;

    public CommonFragTabHost(Context context) {
        this(context, null, 0);
    }

    public CommonFragTabHost(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonFragTabHost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
        setClipChildren(true);
        setClipToPadding(true);

        mContext = context;
        mTabsContainer = new LinearLayout(context);
        addView(mTabsContainer);

        obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonFragTabHost);

        mUnderlineColor = ta.getColor(R.styleable.CommonFragTabHost_tl_underline_color, Color.parseColor("#ffffff"));
        mUnderlineHeight = ta.getDimension(R.styleable.CommonFragTabHost_tl_underline_height, dp2px(0));
        mUnderlineGravity = ta.getInt(R.styleable.CommonFragTabHost_tl_underline_gravity, Gravity.TOP);

        mTextSize = ta.getDimension(R.styleable.CommonFragTabHost_tl_textSize, 13);
        mTextSelectedColor = ta.getColor(R.styleable.CommonFragTabHost_tl_textSelectedColor, Color.parseColor("#ffffff"));
        mTextUnselectedColor = ta.getColor(R.styleable.CommonFragTabHost_tl_textUnselectedColor, Color.parseColor("#AAffffff"));
        mTextBold = ta.getBoolean(R.styleable.CommonFragTabHost_tl_textBold, false);
        mTextAllCaps = ta.getBoolean(R.styleable.CommonFragTabHost_tl_textAllCaps, false);

        mIconVisible = ta.getBoolean(R.styleable.CommonFragTabHost_tl_iconVisible, true);
        mIconGravity = ta.getInt(R.styleable.CommonFragTabHost_tl_iconGravity, Gravity.TOP);
        mIconWidth = ta.getDimension(R.styleable.CommonFragTabHost_tl_iconWidth, dp2px(0));
        mIconHeight = ta.getDimension(R.styleable.CommonFragTabHost_tl_iconHeight, dp2px(0));
        mIconMargin = ta.getDimension(R.styleable.CommonFragTabHost_tl_iconMargin, dp2px(2));
        ta.recycle();

        if (mIconGravity == Gravity.LEFT) {
            tabItemRes = R.layout.layout_tab_start;
        } else if (mIconGravity == Gravity.RIGHT) {
            tabItemRes = R.layout.layout_tab_end;
        } else if (mIconGravity == Gravity.BOTTOM) {
            tabItemRes = R.layout.layout_tab_bottom;
        } else if (mIconGravity == Gravity.TOP) {
            tabItemRes = R.layout.layout_tab_top;
        }

    }

    public void setTabItemRes(int res) {
        tabItemRes = res;
    }

    public void setTabData(List<TabEntity> tabEntities) {
        if (tabEntities == null || tabEntities.size() == 0) {
            throw new IllegalStateException("TabEntities can not be NULL or EMPTY !");
        }
        mTabEntities.clear();
        mTabEntities.addAll(tabEntities);

        notifyDataSetChanged();
    }

    /**
     * 关联数据支持同时切换fragments
     */
    public void setTabData(List<TabEntity> tabEntities, FragmentActivity fa, int containerViewId, ArrayList<Fragment> fragments) {
        mFragmentChangeManager = new FragmentChangeManager(fa.getSupportFragmentManager(), containerViewId, fragments);
        setTabData(tabEntities);
    }

    /**
     * 更新数据
     */
    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        mTabCount = mTabEntities.size();
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            tabView = View.inflate(mContext, tabItemRes, null);
            tabView.setTag(i);
            addTab(i, tabView);
        }
        updateTabStyles();
    }

    /**
     * 创建并添加tab
     */
    private void addTab(final int position, View tabView) {
        TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
        tv_tab_title.setText(mTabEntities.get(position).getTabTitle());
        ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
        iv_tab_icon.setImageResource(mTabEntities.get(position).getTabUnselectedIcon());

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                if (mCurrentTab != position) {
                    setCurrentTab(position);
                    if (mListener != null) {
                        mListener.onTabSelected(position);
                    }
                } else {
                    if (mListener != null) {
                        mListener.onTabReselected(position);
                    }
                }
            }
        });

        /* 每一个Tab的布局参数 */
        LinearLayout.LayoutParams lp_tab = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        mTabsContainer.addView(tabView, position, lp_tab);
    }

    private void updateTabStyles() {
        for (int i = 0; i < mTabCount; i++) {
            View tabView = mTabsContainer.getChildAt(i);

            TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
            tv_tab_title.setTextColor(i == mCurrentTab ? mTextSelectedColor : mTextUnselectedColor);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            if (mTextAllCaps) {
                tv_tab_title.setText(tv_tab_title.getText().toString().toUpperCase());
            }
            if (mTextBold) {
                tv_tab_title.getPaint().setFakeBoldText(mTextBold);
            }

            ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
            if (mIconVisible) {
                iv_tab_icon.setVisibility(View.VISIBLE);
                TabEntity tabEntity = mTabEntities.get(i);
                iv_tab_icon.setImageResource(i == mCurrentTab ? tabEntity.getTabSelectedIcon() : tabEntity.getTabUnselectedIcon());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        mIconWidth <= 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : (int) mIconWidth,
                        mIconHeight <= 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : (int) mIconHeight);
                if (mIconGravity == Gravity.LEFT) {
                    lp.rightMargin = (int) mIconMargin;
                } else if (mIconGravity == Gravity.RIGHT) {
                    lp.leftMargin = (int) mIconMargin;
                } else if (mIconGravity == Gravity.BOTTOM) {
                    lp.topMargin = (int) mIconMargin;
                } else if (mIconGravity == Gravity.TOP) {
                    lp.bottomMargin = (int) mIconMargin;
                }
                iv_tab_icon.setLayoutParams(lp);
            } else {
                iv_tab_icon.setVisibility(View.GONE);
            }
        }
    }

    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title = tabView.findViewById(R.id.tv_tab_title);
            tab_title.setTextColor(isSelect ? mTextSelectedColor : mTextUnselectedColor);
            ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
            TabEntity tabEntity = mTabEntities.get(i);
            iv_tab_icon.setImageResource(isSelect ? tabEntity.getTabSelectedIcon() : tabEntity.getTabUnselectedIcon());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || mTabCount <= 0) {
            return;
        }

        int height = getHeight();
        // draw underline
        if (mUnderlineHeight > 0) {
            mPaint.setColor(mUnderlineColor);
            if (mUnderlineGravity == Gravity.TOP) {
                canvas.drawRect(0, 0, mTabsContainer.getWidth(), mUnderlineHeight, mPaint);
            } else {
                canvas.drawRect(0, height - mUnderlineHeight, mTabsContainer.getWidth(), height, mPaint);
            }
        }
    }

    public void setCurrentTab(int currentTab) {
        mCurrentTab = currentTab;
        updateTabSelection(currentTab);
        if (mFragmentChangeManager != null) {
            mFragmentChangeManager.setFragments(currentTab);
        }
    }

    public void setUnderlineColor(int underlineColor) {
        this.mUnderlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineHeight(float underlineHeight) {
        this.mUnderlineHeight = dp2px(underlineHeight);
        invalidate();
    }

    public void setUnderlineGravity(int underlineGravity) {
        this.mUnderlineGravity = underlineGravity;
        invalidate();
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        updateTabStyles();
    }

    public void setTextSelectedColor(int textSelectedColor) {
        this.mTextSelectedColor = textSelectedColor;
        updateTabStyles();
    }

    public void setTextUnselectedColor(int textUnselectedColor) {
        this.mTextUnselectedColor = textUnselectedColor;
        updateTabStyles();
    }

    public void setTextBold(boolean textBold) {
        this.mTextBold = textBold;
        updateTabStyles();
    }

    public void setTextAllCaps(boolean textAllCaps) {
        this.mTextAllCaps = textAllCaps;
        updateTabStyles();
    }

    public void setIconVisible(boolean iconVisible) {
        this.mIconVisible = iconVisible;
        updateTabStyles();
    }

    public void setIconGravity(int iconGravity) {
        this.mIconGravity = iconGravity;
        notifyDataSetChanged();
    }

    public void setIconWidth(float iconWidth) {
        this.mIconWidth = dp2px(iconWidth);
        updateTabStyles();
    }

    public void setIconHeight(float iconHeight) {
        this.mIconHeight = dp2px(iconHeight);
        updateTabStyles();
    }

    public void setIconMargin(float iconMargin) {
        this.mIconMargin = dp2px(iconMargin);
        updateTabStyles();
    }

    public int getTabCount() {
        return mTabCount;
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public int getUnderlineColor() {
        return mUnderlineColor;
    }

    public float getUnderlineHeight() {
        return mUnderlineHeight;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public int getTextSelectedColor() {
        return mTextSelectedColor;
    }

    public int getTextUnselectedColor() {
        return mTextUnselectedColor;
    }

    public boolean isTextBold() {
        return mTextBold;
    }

    public boolean isTextAllCaps() {
        return mTextAllCaps;
    }

    public int getIconGravity() {
        return mIconGravity;
    }

    public float getIconWidth() {
        return mIconWidth;
    }

    public float getIconHeight() {
        return mIconHeight;
    }

    public float getIconMargin() {
        return mIconMargin;
    }

    public boolean isIconVisible() {
        return mIconVisible;
    }

    public ImageView getIconView(int tab) {
        View tabView = mTabsContainer.getChildAt(tab);
        return tabView.findViewById(R.id.iv_tab_icon);
    }

    public TextView getTitleView(int tab) {
        View tabView = mTabsContainer.getChildAt(tab);
        return tabView.findViewById(R.id.tv_tab_title);
    }

    /**
     * 显示未读消息
     *
     * @param position 显示tab位置
     * @param num      num小于等于0显示红点,num大于0显示数字
     */
    private void showMsg(int position, float leftMargin, float topMargin, int num) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }

        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = tabView.findViewById(R.id.msg_tip);
        if (tipView != null) {
            MarginLayoutParams lp = (MarginLayoutParams) tipView.getLayoutParams();
            lp.leftMargin = dp2px(leftMargin);
            if (!mIconVisible) {
                lp.topMargin = dp2px(topMargin);
            }
            tipView.setLayoutParams(lp);
            UnreadMsgUtils.showMsg(tipView, num, true, true);
        }
    }

    /**
     * 显示未读红点
     *
     * @param position
     * @param leftMargin
     */
    public void showRedDot(int position, float leftMargin) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }
        showMsg(position, leftMargin, -5, 0);
    }

    /**
     * 显示未读消息
     *
     * @param position
     * @param leftMargin
     * @param num
     */
    public void showRedMsg(int position, float leftMargin, int num) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }
        showMsg(position, leftMargin, -10, num);
    }

    /**
     * 隐藏小红点或消息
     * @param position
     */
    public void hideDotOrMsg(int position) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }
        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = tabView.findViewById(R.id.msg_tip);
        if (tipView != null) {
            tipView.setVisibility(View.GONE);
        }
    }

    private OnTabSelectListener mListener;

    public void setOnTabSelectListener(OnTabSelectListener listener) {
        mListener = listener;
    }

    public interface OnTabSelectListener {
        void onTabSelected(int tabPos);

        void onTabReselected(int tabPos);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mCurrentTab", mCurrentTab);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentTab = bundle.getInt("mCurrentTab");
            state = bundle.getParcelable("instanceState");
            if (mCurrentTab != 0 && mTabsContainer.getChildCount() > 0) {
                updateTabSelection(mCurrentTab);
            }
        }
        super.onRestoreInstanceState(state);
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
