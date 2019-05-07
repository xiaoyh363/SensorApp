package com.xiaoyh.retrofit.mvp.model

import com.xiaoyh.retrofit.bean.reception.LoginReception
import com.xiaoyh.retrofit.bean.request.UserBean
import com.xiaoyh.retrofit.util.retrofit.RxJavaRetrofitUtil
import rx.Subscriber

object LoginModel {

    private val rxJavaRetrofitUtil = RxJavaRetrofitUtil()

    fun login(userBean: UserBean, subscriber: Subscriber<LoginReception>) {
        rxJavaRetrofitUtil.loginPost(userBean, subscriber)
    }
}