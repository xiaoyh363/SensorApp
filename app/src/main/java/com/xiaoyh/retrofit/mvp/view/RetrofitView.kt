package com.xiaoyh.retrofit.mvp.view

import com.xiaoyh.retrofit.mvp.base.BaseView

interface RetrofitView<T> : BaseView {
    fun onResult(result: T)
    fun onCompleted()
    fun onError(e: Throwable)
}