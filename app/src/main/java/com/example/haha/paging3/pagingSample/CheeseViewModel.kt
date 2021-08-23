package com.example.haha.paging3.pagingSample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class CheeseViewModel(private val dao:CheeseDao):ViewModel() {

    val allCheeses: Flow<PagingData<CheeseListItem>> = Pager(
        config = PagingConfig(
            pageSize = 60,
            enablePlaceholders = true,
            maxSize = 200
        )
    ){
        dao.allCheesesByName()
    }.flow.map {
        pagingData ->
        pagingData.map {
            cheese -> CheeseListItem.Item(cheese)
        }.insertSeparators{before:CheeseListItem?,after:CheeseListItem?->
            if (before == null && after == null){
                null
            }else if (after == null){
                null
            }else if (before == null){
                CheeseListItem.Separator(after.name.first())
            }else if (before.name.first() != after.name.first()){
                CheeseListItem.Separator(after.name.first())
            }else{
                null
            }
        }
    }.cachedIn(viewModelScope)

    fun insert(text:CharSequence) = ioThread{
        dao.insert(Cheese(id = 0,name = text.toString()))
    }

    fun remove(cheese: Cheese) = ioThread {
        dao.delete(cheese)
    }
}