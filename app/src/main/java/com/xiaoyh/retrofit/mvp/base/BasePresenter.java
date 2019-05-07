package com.xiaoyh.retrofit.mvp.base;

import java.lang.ref.WeakReference;

public class BasePresenter<V extends BaseView> {
    protected V view;
    private WeakReference<V> weakView;

    void attachView(V view) {
        this.weakView = new WeakReference<>(view);
        this.view = weakView.get();
    }

    void detachView() {
        if (view != null) {
            view = null;
            weakView.clear();
            weakView = null;
        }
    }
}
