package com.example.nytimesoverview.injections.module

import android.app.Application
import androidx.room.Room
import com.example.nytimesoverview.database.DataBaseNewsApi
import com.example.nytimesoverview.database.NYTimesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(application: Application) {

    val application = application

    @Provides
    @Singleton
    internal fun provideDatabaseNewsApi(database: NYTimesDatabase): DataBaseNewsApi {
        return database.newsDao
    }

    @Provides
    @Singleton
    internal fun provideNYTimesDatabase(): NYTimesDatabase {
        return Room.databaseBuilder(this.application,
            NYTimesDatabase::class.java,
            "news").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}