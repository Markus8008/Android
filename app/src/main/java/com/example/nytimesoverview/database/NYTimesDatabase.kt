package com.example.nytimesoverview.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class, CommentEntity::class], version = 4, exportSchema = false)
abstract class NYTimesDatabase: RoomDatabase() {
    abstract val newsDao: DataBaseNewsApi
    //abstract val commentsDao: CommentsDao
}