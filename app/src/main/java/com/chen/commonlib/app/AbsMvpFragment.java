package com.chen.commonlib.app;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.chen.api.base.BaseMvpFragment;
import com.chen.api.base.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class AbsMvpFragment<P extends BasePresenter> extends BaseMvpFragment<P> {
    private Unbinder unbinder;

    @Override
    protected void initButterKnife(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
    }

    protected AbsMvpActivity getFragActivity() {
        FragmentActivity activity = getActivity();
        return activity != null ? (AbsMvpActivity) activity : null;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
