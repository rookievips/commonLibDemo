package com.chen.api.base;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("all")
public abstract class BasePresenter<V> {
    public V baseView;

    /**
     * 简单页面无需mvp就不用管此方法即可,需要mvp就在attachPresenter()里调用绑定对应的Activity,完美兼容各种实际场景的变通
     *
     * @param baseView
     */
    public void attachView(V baseView) {
        this.baseView = baseView;
    }


    protected void addSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(observer);
    }

    public void detachView() {
        if (baseView != null)
            baseView = null;
    }
}
