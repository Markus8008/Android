package com.example.nytimesoverview.network

import com.example.nytimesoverview.database.NewsEntity
import com.example.nytimesoverview.news.overview.model.NewsProperties
import com.google.gson.annotations.SerializedName

data class ArticleMostPopularData(
    val status: String,
    val copyright: String,
    val results: List<ArticeProperties>
)

data class ArticeProperties (
    val id: String,
    val title: String,
    @SerializedName("published_date")val publishedDate: String,
    val updated: String,
    val url: String,
    val media: List<ArticleMediaProperties> = emptyList()
)

data class ArticleMediaProperties (
    val type: String,
    val caption: String,
    @SerializedName("media-metadata")
    val mediaMetadata: List<ArticleMediaMetadataProperties> = emptyList()
)

data class ArticleMediaMetadataProperties (
    val url: String,
    val format: String
)

fun ArticleMostPopularData.convertToEntity(): List<NewsEntity> {

    val result = ArrayList<NewsEntity>()
    for(article:ArticeProperties in results) {
        result.add(NewsEntity(article.id, article.title, article.url, article.publishedDate, article.media[0]?.mediaMetadata[2]?.url))
    }
    return result
}

fun ArticleMostPopularData.convertToDomain(): List<NewsProperties> {

    val result = ArrayList<NewsProperties>()
    for(article:ArticeProperties in results) {
        result.add(NewsProperties(article.id, article.title, article.url, article.publishedDate, article.media[0].mediaMetadata[2].url))
    }
    return result
}