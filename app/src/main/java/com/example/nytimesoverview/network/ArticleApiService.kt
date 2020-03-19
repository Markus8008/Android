package com.example.nytimesoverview.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/shared/"
const val API_KEY = "j4BFUiBWVFB5IGpyJLIVC6qIucA3o67p"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

enum class NewsPeriodFilter {
    THIRTY_DAYS,
    SEVEN_DAYS,
    LAST_DAY }

interface NYTimesArticleApiService {

    @GET("30/facebook.json")
    fun getArticlesThirtyPeriod(@Query("api-key") type: String): Deferred<ArticleMostPopularData>

    @GET("7/facebook.json")
    fun getArticlesSevenPeriod(@Query("api-key") type: String): Deferred<ArticleMostPopularData>

    @GET("1/facebook.json")
    fun getArticlesLastDayPeriod(@Query("api-key") type: String): Deferred<ArticleMostPopularData>
}

object NYTimesArticleApi {
    val retrofitService : NYTimesArticleApiService by lazy {
        retrofit.create(NYTimesArticleApiService::class.java) }
}