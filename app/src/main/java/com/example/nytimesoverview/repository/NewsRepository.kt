package com.example.nytimesoverview.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.nytimesoverview.network.*
import com.example.nytimesoverview.base.BaseNewsRepository
import com.example.nytimesoverview.database.*
import com.example.nytimesoverview.injections.component.DaggerNewsRepositoryInjector
import com.example.nytimesoverview.injections.module.ContextModule
import com.example.nytimesoverview.injections.module.DatabaseModule
import com.example.nytimesoverview.injections.module.NetworkModule
import com.example.nytimesoverview.news.overview.model.NewsComment
import com.example.nytimesoverview.news.overview.model.NewsProperties
import com.example.nytimesoverview.news.overview.model.toCommentEntity
import com.example.nytimesoverview.news.overview.model.toNewsEntity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Appendable
import javax.inject.Inject


class NewsRepository(private val database: NewsDatabase, application: Application)/*: BaseNewsRepository()*/ {

    @Inject
    lateinit var nyTimesApi: NYTimesApi

    @Inject
    lateinit var databaseNewsApi: DataBaseNewsApi

    init {
        DaggerNewsRepositoryInjector
            .builder()
            .networkModule(NetworkModule)
            //.contextModule(ContextModule)
            .databaseModule(DatabaseModule(application))
            .build()
            .inject(this)
    }

    val newsFromRepo: LiveData<List<NewsProperties>> = Transformations.map(database.newsDao.getNews()) {
        it.asDomainNewsModel()
    }

    fun getCommentsForNews(idNews: String): LiveData<List<NewsComment>> {
        val commentsFromRepo: LiveData<List<NewsComment>> =
            Transformations.map(database.commentsDao.getComments(idNews)) {
                it.asDomainCommentModel()
            }
        return commentsFromRepo
    }

    fun addCommentForNews(news: NewsProperties, comment: NewsComment) {
        //database.newsDao.saveNewsEntity(news.toNewsEntity())
        databaseNewsApi.saveNewsEntity(news.toNewsEntity())
        database.commentsDao.saveCommentEntity(comment.toCommentEntity())
    }

    suspend fun refreshNewsData(filter: NewsPeriodFilter) {
        withContext(Dispatchers.IO) {
            var getArticlesDeferred = chooseNewsApi(filter)
            var articlesResult = getArticlesDeferred.await()
            //database.newsDao.deleteAllNews()
            System.out.println("#### news size: "+database.newsDao.getNewsDirectly().size)
            database.newsDao.insertAll(articlesResult.convertToEntity())
        }
    }

    suspend fun refreshNews(filter: NewsPeriodFilter): List<NewsProperties>{
        return withContext(Dispatchers.IO) {
            var getArticlesDeferred = chooseNewsApi(filter)
            var articlesResult = getArticlesDeferred.await()
            return@withContext articlesResult.convertToDomain()
        }
    }

    private fun chooseNewsApi(filter: NewsPeriodFilter): Deferred<ArticleMostPopularData> {
        var getArticlesDeferred: Deferred<ArticleMostPopularData>
        when (filter) {
            NewsPeriodFilter.SEVEN_DAYS -> {
                getArticlesDeferred =
                    /*NYTimesArticleApi.retrofitService*/nyTimesApi.getArticlesSevenPeriod(API_KEY)
            }
            NewsPeriodFilter.LAST_DAY -> {
                getArticlesDeferred =
                    /*NYTimesArticleApi.retrofitService*/nyTimesApi.getArticlesLastDayPeriod(API_KEY)
            }
            else -> {
                getArticlesDeferred =
                    /*NYTimesArticleApi.retrofitService*/nyTimesApi.getArticlesThirtyPeriod(API_KEY)
            }
        }
        return getArticlesDeferred
    }
}