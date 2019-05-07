package com.xiaoyh.retrofit.util.retrofit.api

import com.xiaoyh.retrofit.bean.reception.BaseReception
import com.xiaoyh.retrofit.bean.reception.LoginReception
import com.xiaoyh.retrofit.bean.request.UserBean
import retrofit2.http.*
import rx.Observable

interface UserApi {

    @POST("login")
    fun loginPost(@Body userBean: UserBean): Observable<LoginReception>

    @POST("register")
    fun registerPost(@Body userBean: UserBean): Observable<BaseReception>
}
