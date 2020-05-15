package com.chen.api.base;

import android.content.Context;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Author: Chen
 * Date: 2018/7/4
 * Desc:
 */
public abstract class BasePresenter<V> {
    public Context mContext;
    public V baseView;
    private CompositeSubscription compositeSubscription;

    /**
     * 简单页面无需mvp就不用管此方法即可,需要mvp就在preInitView()里调用绑定对应的Activity,完美兼容各种实际场景的变通
     * @param baseView
     */
    public void attachView(V baseView) {
        this.baseView = baseView;
    }

    @SuppressWarnings("unchecked")
    protected void addSubscribe(Observable observable, Subscriber subscriber) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void detachView() {
        this.baseView = null;
        unSubscribe();
    }

    private void unSubscribe() {
        if (compositeSubscription != null) {
            if (!compositeSubscription.isUnsubscribed()) {
                compositeSubscription.unsubscribe();
            }
        }
    }

    public boolean isSuccess(int statusCode) {
        //一般"200"表示成功,主要还是根据后台定
        return 200 == statusCode;
    }

}
