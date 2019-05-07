package com.xiaoyh.retrofit.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity {

    private V view;
    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (view == null) view = createView();
        if (presenter == null) presenter = createPresenter();
        if (view != null && presenter != null) presenter.attachView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public P getPresenter() {
        return presenter;
    }

    protected abstract V createView();

    protected abstract P createPresenter();
}
