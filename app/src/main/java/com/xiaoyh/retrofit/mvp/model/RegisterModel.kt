package com.xiaoyh.retrofit.mvp.model

import com.xiaoyh.retrofit.bean.reception.BaseReception
import com.xiaoyh.retrofit.bean.request.UserBean
import com.xiaoyh.retrofit.util.retrofit.RxJavaRetrofitUtil
import rx.Subscriber

object RegisterModel {

    private val rxJavaRetrofitUtil = RxJavaRetrofitUtil()

    fun register(userBean: UserBean, subscriber: Subscriber<BaseReception>) {
        rxJavaRetrofitUtil.registerPost(userBean, subscriber)
    }
}