package com.example.nytimesoverview.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataBaseNewsApi {

    @Query("select * from NewsEntity")
    fun getNews(): LiveData<List<NewsEntity>>

    @Query("select * from NewsEntity")
    fun getNewsDirectly(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( news: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNewsEntity(news: NewsEntity)

}