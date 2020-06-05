package com.chen.api.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.api.R;
import com.chen.api.helper.ActManager;
import com.chen.api.helper.DisposableManager;
import com.chen.api.utils.StatusBarUtil;
import com.chen.api.utils.TUtil;
import com.chen.api.utils.ToastUtil;

import java.util.Locale;


public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected Activity mActivity;
    protected Context mContext;
    protected P mPresenter;
    protected View mainView;
    private View titleBar;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = this;
        try {
            mPresenter = TUtil.getT(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        doBeforeSetContentView();
        setContentView(layoutResId());
        initButterKnife();
        attachPresenter();
        initView();
        afterCreated();
    }

    /**
     * 设置layout之前配置
     */
    protected void doBeforeSetContentView() {
        //把activity放到Activity栈中管理
        ActManager.getInstance().addActivity(this);
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarUtil.setStatusBar(this, Color.parseColor("#ffffff"), true);
    }

    protected abstract int layoutResId();

    protected abstract void initButterKnife();

    protected abstract void attachPresenter();

    /**
     * view初始化
     */
    protected abstract void initView();

    /**
     * view初始化完成之后
     */
    protected abstract void afterCreated();


    @Override
    public void setContentView(int layoutResId) {
        RelativeLayout rootView = new RelativeLayout(this);
        rootView.removeAllViews();
        titleBar = initTitleBar();
        mainView = inflaterView(layoutResId, rootView);

        if (titleBar != null) {
            RelativeLayout.LayoutParams lpTitleBar = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp_47));
            rootView.addView(titleBar, lpTitleBar);
            RelativeLayout.LayoutParams lpMain = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            lpMain.addRule(RelativeLayout.BELOW, titleBar.getId());
            rootView.addView(mainView, lpMain);
        } else {
            rootView.addView(mainView);
        }

        super.setContentView(rootView);
    }

    /**
     * 子类可以重写此方法根据需求定制不同的titleBar,当不需要titleBar的时候可以重写返回null
     *
     * @return
     */
    protected View initTitleBar() {
        return inflaterView(R.layout.common_title_bar, null);
    }


    public View inflaterView(int resource, ViewGroup root) {
        return getLayoutInflater().inflate(resource, root, false);
    }


    /**
     * @param title
     * @return
     */
    protected BaseMvpActivity commonTitleBarStyle(String title) {
        commonTitleBarStyle(
                Color.parseColor("#FFFFFF"),
                title,
                Color.parseColor("#313131"),
                true,
                true);
        return this;
    }

    /**
     * @param bgColor
     * @param title
     * @param titleColor
     * @param showBack
     * @param showElevation
     */
    protected BaseMvpActivity commonTitleBarStyle(int bgColor, String title, int titleColor, boolean showBack, boolean showElevation) {

        getTitleBar().setBackgroundColor(bgColor);//标题栏背景颜色

        ((TextView) getTitleBar().findViewById(R.id.txt_title_bar_title)).setText(title);//标题
        ((TextView) getTitleBar().findViewById(R.id.txt_title_bar_title)).setTextColor(titleColor);//标题字体颜色

        if (showBack) {//是否显示返回按钮
            getTitleBar().findViewById(R.id.img_title_bar_left).setVisibility(View.VISIBLE);
            getTitleBar().findViewById(R.id.img_title_bar_left).setOnClickListener(v -> backClick());
        } else {
            getTitleBar().findViewById(R.id.img_title_bar_left).setVisibility(View.GONE);
        }

        if (showElevation) {//是否显示底部阴影线条
            getTitleBar().findViewById(R.id.txt_elevation).setVisibility(View.VISIBLE);
        } else {
            getTitleBar().findViewById(R.id.txt_elevation).setVisibility(View.GONE);
        }
        return this;
    }

    public BaseMvpActivity imgLeftRes(int imgRes) {
        ((ImageView) getTitleBar().findViewById(R.id.img_title_bar_left)).setImageDrawable(ContextCompat.getDrawable(this, imgRes));
        return this;
    }


    public BaseMvpActivity imgTitleRes(int imgRes, View.OnClickListener onClickListener) {
        ((ImageView) getTitleBar().findViewById(R.id.img_title_bar_title)).setImageDrawable(ContextCompat.getDrawable(this, imgRes));
        getTitleBar().findViewById(R.id.img_title_bar_title).setVisibility(View.VISIBLE);
        getTitleBar().findViewById(R.id.img_title_bar_title).setOnClickListener(onClickListener);
        getTitleBar().findViewById(R.id.txt_title_bar_title).setOnClickListener(onClickListener);
        return this;
    }

    public BaseMvpActivity txtRightOneContent(String content, View.OnClickListener onClickListener) {
        ((TextView) getTitleBar().findViewById(R.id.txt_title_bar_right_one)).setText(content);
        getTitleBar().findViewById(R.id.txt_title_bar_right_one).setVisibility(View.VISIBLE);
        getTitleBar().findViewById(R.id.txt_title_bar_right_one).setOnClickListener(onClickListener);
        return this;
    }

    public BaseMvpActivity txtRightTwoContent(String content, View.OnClickListener onClickListener) {
        ((TextView) getTitleBar().findViewById(R.id.txt_title_bar_right_two)).setText(content);
        getTitleBar().findViewById(R.id.txt_title_bar_right_two).setVisibility(View.VISIBLE);
        getTitleBar().findViewById(R.id.txt_title_bar_right_two).setOnClickListener(onClickListener);
        return this;
    }

    public BaseMvpActivity imgRightOneRes(int imgRes, View.OnClickListener onClickListener) {
        ((ImageView) getTitleBar().findViewById(R.id.img_title_bar_right_one)).setImageDrawable(ContextCompat.getDrawable(this, imgRes));
        getTitleBar().findViewById(R.id.img_title_bar_right_one).setVisibility(View.VISIBLE);
        getTitleBar().findViewById(R.id.img_title_bar_right_one).setOnClickListener(onClickListener);
        return this;
    }

    public BaseMvpActivity imgRightTwoRes(int imgRes, View.OnClickListener onClickListener) {
        ((ImageView) getTitleBar().findViewById(R.id.img_title_bar_right_two)).setImageDrawable(ContextCompat.getDrawable(this, imgRes));
        getTitleBar().findViewById(R.id.img_title_bar_right_two).setVisibility(View.VISIBLE);
        getTitleBar().findViewById(R.id.img_title_bar_right_two).setOnClickListener(onClickListener);
        return this;
    }

    public View getTitleBar() {
        if (titleBar == null) {
            throw new NullPointerException("titleBar is null,please init titleBar");
        }
        return titleBar;
    }

    protected void backClick() {
        finish();
        //页面跳转动画
//        overridePendingTransition(R.anim.slide_in_from_left_common, R.anim.slide_out_to_right_common);
    }

    /**
     * Android6.0权限检测
     *
     * @param permission
     * @return
     */
    protected boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Android6.0权限申请
     *
     * @param permissionReqCode
     * @param permissions
     */
    protected void requestPermission(int permissionReqCode, String... permissions) {
        ActivityCompat.requestPermissions(mActivity, permissions, permissionReqCode);
    }

    /**
     * 当用户拒绝给权限的时候调用，跳转权限设置页面，让用户手动给权限
     *
     * @param activity       Activity对象
     * @param permissionName 权限名称
     * @param msg            提示信息
     */
    protected void setupPermission(Activity activity, String permissionName, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle("权限申请")
                .setMessage(String.format(Locale.getDefault(), "请在“权限”中开启“%1s权限”，以正常使用%2s", permissionName, msg))
                .setCancelable(false)
//                .setNegativeButton("取消", (dialog1, which) -> dialog1.dismiss())
                .setPositiveButton("去设置", (dialog12, which) -> activity.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", activity.getPackageName(), null))))
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#008577"));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#008577"));
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void showLoading() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        mContext = null;
        mActivity = null;
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        DisposableManager.getInstance().cancelAll();
        ActManager.getInstance().finishActivity(this);
        super.onDestroy();
    }
}
