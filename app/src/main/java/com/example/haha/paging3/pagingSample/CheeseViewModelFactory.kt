package com.example.haha.paging3.pagingSample

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheeseViewModelFactory(private val app:Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheeseViewModel::class.java)){
            val cheeseDao = CheeseDb.get(app).cheeseDao()
            return CheeseViewModel(cheeseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}