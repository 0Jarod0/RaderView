package com.example.haha.paging3.pagingWithNet

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.withContext

class DataSource :PagingSource<Int,Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            var currentPage = params.key ?: 0

            var demoReqData = DataRespority().loadData(currentPage)

            var nextPage = if (currentPage < demoReqData?.data()?.pageCount!!){
                currentPage+1
            }else{
                null
            }

            LoadResult.Page(
                data = demoReqData.data().datas,
                prevKey = currentPage-1,
                nextKey = nextPage
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int ?= null
}