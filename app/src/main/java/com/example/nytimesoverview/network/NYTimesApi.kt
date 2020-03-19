package com.example.nytimesoverview.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTimesApi {

    @GET("30/facebook.json")
    fun getArticlesThirtyPeriod(@Query("api-key") type: String): Deferred<ArticleMostPopularData>

    @GET("7/facebook.json")
    fun getArticlesSevenPeriod(@Query("api-key") type: String): Deferred<ArticleMostPopularData>

    @GET("1/facebook.json")
    fun getArticlesLastDayPeriod(@Query("api-key") type: String): Deferred<ArticleMostPopularData>

}