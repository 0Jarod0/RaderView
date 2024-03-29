package com.example.haha.room.repository

import androidx.annotation.WorkerThread
import com.example.haha.room.dao.WordDao
import com.example.haha.room.data.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    val allWords:Flow<List<Word>> = wordDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word){
        wordDao.insert(word)
    }
}