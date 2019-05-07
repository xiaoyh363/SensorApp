package com.xiaoyh.retrofit.util.retrofit.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Apis {

    private var url = "http://192.168.137.1:5000/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())         // 设置数据解析器
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   // 设置网络请求适配器
        .build()

    private val userApi = retrofit.create(UserApi::class.java)
    private val testApi = retrofit.create(TestApi::class.java)

    fun getUserApi(): UserApi {
        return userApi
    }

    fun getTestApi(): TestApi {
        return testApi
    }
}