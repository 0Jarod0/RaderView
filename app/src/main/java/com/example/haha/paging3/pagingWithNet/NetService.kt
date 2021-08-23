package com.example.haha.paging3.pagingWithNet

import com.example.haha.coroutine.network.BaseReq
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetService {

    @GET("/article/listproject/{pageIndex}/json")
    suspend fun getArticle(@Path("pageIndex") pageIndex:Int):BaseReq<Article>
}