package com.chen.api.helper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposableManager {

    private static DisposableManager instance;
    private CompositeDisposable disposables;

    private DisposableManager() {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
    }

    public void add(Disposable d) {
        if (d == null) return;
        disposables.add(d);
    }

    public void cancel(Disposable d) {
        if (disposables != null) {
            disposables.delete(d);
        }
    }

    public void cancelAll() {
        if (disposables != null) {
            disposables.clear();
        }
    }

    public static DisposableManager getInstance() {
        if (instance == null) {
            synchronized (DisposableManager.class) {
                if (instance == null) {
                    instance = new DisposableManager();
                }
            }
        }
        return instance;
    }

}
