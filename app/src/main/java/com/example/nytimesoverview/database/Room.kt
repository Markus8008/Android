package com.example.nytimesoverview.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDao {

    @Query("select * from NewsEntity")
    fun getNews(): LiveData<List<NewsEntity>>

    @Query("select * from NewsEntity")
    fun getNewsDirectly(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( news: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNewsEntity(news: NewsEntity)

//    @Query("delete from NewsEntity")
//    fun deleteAllNews()
}

@Dao
interface CommentsDao {

    @Query("select * from CommentEntity where id_news = :idNews")
    fun getComments(idNews: String): LiveData<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCommentEntity(news: CommentEntity)
}

@Database(entities = [NewsEntity::class, CommentEntity::class], version = 4, exportSchema = false)
abstract class NewsDatabase: RoomDatabase() {
    abstract val newsDao: NewsDao
    abstract val commentsDao: CommentsDao
}

private lateinit var INSTANCE: NewsDatabase
fun getDatabase(context: Context): NewsDatabase {
    synchronized(NewsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                NewsDatabase::class.java,
                "news").allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}