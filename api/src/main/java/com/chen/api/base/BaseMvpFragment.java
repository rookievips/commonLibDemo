package com.chen.api.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.api.helper.DisposableManager;
import com.chen.api.utils.TUtil;


public abstract class BaseMvpFragment<P extends BasePresenter> extends Fragment implements BaseView {
    protected View rootView;
    protected P mPresenter;
    private boolean isVisibleToUser = false;//fragment是否可见
    private boolean isViewCreated = false;//View是否加载完成


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(layoutResId(), container, false);
            initView(rootView);
        }
        initButterKnife(rootView);
        try {
            mPresenter = TUtil.getT(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        attachPresenter();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        lazyLoad();
    }

    /**
     * 懒加载
     */
    private void lazyLoad(){
        if (!isVisibleToUser || !isViewCreated) {
            return;
        }
        afterCreated();
    }

    protected abstract void initButterKnife(View rootView);

    protected abstract int layoutResId();

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    protected abstract void attachPresenter();

    /**
     * 初始化View
     */
    protected abstract void initView(View rootView);


    /**
     * 做网络请求获取传值等操作
     */
    protected abstract void afterCreated();



    @Override
    public void showErrorMsg(String msg) {
        if (getActivity() == null)
            return;
        ((BaseMvpActivity)getActivity()).showErrorMsg(msg);
    }

    @Override
    public void showLoading() {
        if (getActivity() == null)
            return;
        ((BaseMvpActivity)getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        if (getActivity() == null)
            return;
        ((BaseMvpActivity)getActivity()).hideLoading();
    }

    @Override
    public void onDestroyView() {
        isViewCreated = false;
        isVisibleToUser = false;
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        DisposableManager.getInstance().cancelAll();
        if (rootView != null) {
            //Android不允许在容器中添加已有父容器的view
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }
}
