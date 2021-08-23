package com.example.haha.paging3.pagingSample

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CheeseDao {

    @Query("SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC")
    fun allCheesesByName(): PagingSource<Int,Cheese>

    @Insert
    fun insert(cheesees:List<Cheese>)

    @Insert
    fun insert(cheese:Cheese)

    @Insert
    fun delete(cheese: Cheese)
}