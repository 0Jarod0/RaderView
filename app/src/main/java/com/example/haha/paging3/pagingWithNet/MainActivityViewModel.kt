package com.example.haha.paging3.pagingWithNet

import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.Pager
import androidx.paging.PagingConfig

class MainActivityViewModel:ViewModel() {

    fun getData() = Pager(PagingConfig(pageSize = 15,prefetchDistance = 3)){
        DataSource()
    }.flow
}