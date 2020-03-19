package com.example.nytimesoverview.news.overview.model

import android.os.Parcelable
import com.example.nytimesoverview.database.CommentEntity
import com.example.nytimesoverview.database.NewsEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsProperties (
    val id: String,
    val title: String,
    val url: String,
    val publishedDate: String,
    val imageUrl: String
): Parcelable

@Parcelize
data class NewsComment(
    val id: Long?,
    val idNews: String,
    val comment: String
): Parcelable

fun NewsProperties.toNewsEntity(): NewsEntity {
    return NewsEntity(id, title, url, publishedDate, imageUrl)
}

fun NewsComment.toCommentEntity(): CommentEntity {
    return CommentEntity(id, idNews, comment)
}