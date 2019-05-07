package com.xiaoyh.retrofit.util.retrofit.api

import com.xiaoyh.retrofit.bean.reception.BaseReception
import com.xiaoyh.retrofit.bean.request.TestBean
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface TestApi {

    @POST("test")
    fun testPost(@Body testBean: TestBean): Observable<BaseReception>
}