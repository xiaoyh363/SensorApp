package com.xiaoyh.retrofit.mvp.presenter

import com.xiaoyh.retrofit.bean.reception.BaseReception
import com.xiaoyh.retrofit.bean.request.UserBean
import com.xiaoyh.retrofit.mvp.base.BasePresenter
import com.xiaoyh.retrofit.mvp.model.RegisterModel
import com.xiaoyh.retrofit.mvp.view.RetrofitView
import rx.Subscriber

class CommonPresenter : BasePresenter<RetrofitView<BaseReception>>() {

    fun register(userBean: UserBean) {
        RegisterModel.register(userBean, object : Subscriber<BaseReception>() {
            override fun onNext(t: BaseReception) {
                view?.onResult(t)
            }

            override fun onCompleted() {
                view?.onCompleted()
            }

            override fun onError(e: Throwable) {
                view?.onError(e)
            }
        })
    }
}