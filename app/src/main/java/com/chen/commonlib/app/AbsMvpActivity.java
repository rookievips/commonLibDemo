package com.chen.commonlib.app;

import com.chen.api.base.BaseMvpActivity;
import com.chen.api.base.BasePresenter;

import butterknife.ButterKnife;


public abstract class AbsMvpActivity<P extends BasePresenter> extends BaseMvpActivity<P> {

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

}
