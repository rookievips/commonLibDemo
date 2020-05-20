package com.chen.commonlib.app;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.chen.api.base.BaseFragment;
import com.chen.api.http.OnRequestListener;
import com.chen.api.http.RequestManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class AbsFragment extends BaseFragment {
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
