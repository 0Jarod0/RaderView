package com.example.haha.paging3.pagingWithNet

import com.example.haha.coroutine.network.BaseReq

class DataRespority {

    private var netWork = RetrofitClient.get().create(NetService::class.java)

    suspend fun loadData(
        pageId: Int
    ):BaseReq<Article>?{
        return try{
            netWork.getArticle(pageId)
        }catch (e:Exception){
            null
        }
    }
}