package com.chen.commonlib.app;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.chen.api.base.BaseFragment;
import com.chen.api.base.BasePresenter;
import com.chen.commonlib.app.option.volley.OnRequestListener;
import com.chen.commonlib.app.option.volley.RequestManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: Chen
 * Date: 2020/5/9 16:51
 * Des:
 */
public abstract class AbsFragment<P extends BasePresenter> extends BaseFragment<P> {
    private Unbinder unbinder;

    @Override
    protected void initButterKnife(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
    }

    protected <T> void request(Object data, boolean needLoading, final OnRequestListener<T> onRequestListener) {
        getFragActivity().request(data, getFragVolleyTag(), needLoading, onRequestListener);
    }


    protected AbsActivity getFragActivity() {
        FragmentActivity activity = getActivity();
        return activity != null ? (AbsActivity) activity : null;
    }

    @Override
    public void invalidToken() {
        getFragActivity().invalidToken();
    }

    /**
     * 获取http请求tag
     *
     * @return
     */
    private String getFragVolleyTag() {
        return getClass().getName();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        RequestManager.getInstance().cancel(getFragVolleyTag());
        super.onDestroyView();
    }
}
