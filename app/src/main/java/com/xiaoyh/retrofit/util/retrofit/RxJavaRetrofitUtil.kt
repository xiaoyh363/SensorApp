package com.xiaoyh.retrofit.util.retrofit

import com.xiaoyh.retrofit.bean.reception.LoginReception
import com.xiaoyh.retrofit.bean.reception.BaseReception
import com.xiaoyh.retrofit.bean.request.UserBean
import com.xiaoyh.retrofit.bean.request.TestBean
import com.xiaoyh.retrofit.util.common.LogUtil
import com.xiaoyh.retrofit.util.retrofit.api.Apis
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RxJavaRetrofitUtil {

    private var subscription: Subscription? = null

    // 此处的 T 指的是“响应”数据的格式
    private fun <T> subscribe(observable: Observable<T>, subscriber: Subscriber<T>) {
        subscription = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)
    }

    fun cancel() {
        subscription?.let {
            LogUtil.d(it.isUnsubscribed.toString())
            if (!it.isUnsubscribed) {
                it.unsubscribe()
            }
        }
    }

    fun loginPost(userBean: UserBean, subscriber: Subscriber<LoginReception>) {
        subscribe(Apis.getUserApi().loginPost(userBean), subscriber)
    }

    fun registerPost(userBean: UserBean, subscriber: Subscriber<BaseReception>) {
        subscribe(Apis.getUserApi().registerPost(userBean), subscriber)
    }

    fun testPost(testBean: TestBean, subscriber: Subscriber<BaseReception>) {
        subscribe(Apis.getTestApi().testPost(testBean), subscriber)
    }
}