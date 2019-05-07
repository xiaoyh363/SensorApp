package com.xiaoyh.retrofit.mvp.presenter

import com.xiaoyh.retrofit.bean.reception.LoginReception
import com.xiaoyh.retrofit.bean.request.UserBean
import com.xiaoyh.retrofit.mvp.view.RetrofitView
import com.xiaoyh.retrofit.mvp.base.BasePresenter
import com.xiaoyh.retrofit.mvp.model.LoginModel
import rx.Subscriber

class LoginPresenter : BasePresenter<RetrofitView<LoginReception>>() {

    fun login(userBean: UserBean) {
        LoginModel.login(userBean, object : Subscriber<LoginReception>() {
            override fun onNext(t: LoginReception) {
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