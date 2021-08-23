package com.example.haha.coroutine.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface DemoService {

    @GET("/article/listproject/0/json")
    fun queryData(): Call<BaseReq<DataReqBean>>

    @GET("/wxarticle/chapters/json ")
    suspend fun queryDataKotlin(): BaseReq<NewBean>

    @GET("/wxarticle/chapters/json ")
    suspend fun queryDataKotlin1(): List<NewBeanItem>
}