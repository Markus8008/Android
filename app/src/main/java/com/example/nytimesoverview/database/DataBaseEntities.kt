package com.example.nytimesoverview.database

import androidx.room.*
import com.example.nytimesoverview.news.overview.model.NewsComment
import com.example.nytimesoverview.news.overview.model.NewsProperties

@Entity
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val url: String,
    val publishedDate: String,
    val imageUrl: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        if (other !is NewsEntity) {
            return false
        }

        return id.equals(other.id)
    }
}

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = NewsEntity::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id_news"),
    onDelete = ForeignKey.NO_ACTION)
))
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "id_news", index = true)val idNews: String,
    val comment: String
)

fun List<NewsEntity>.asDomainNewsModel(): List<NewsProperties> {
    return map {
        NewsProperties(
            it.id,
            it.title,
            it.url,
            it.publishedDate,
            it.imageUrl
        )
    }
}

fun List<CommentEntity>.asDomainCommentModel(): List<NewsComment> {
    return map {
        NewsComment(
            it.id,
            it.idNews,
            it.comment
        )
    }
}
