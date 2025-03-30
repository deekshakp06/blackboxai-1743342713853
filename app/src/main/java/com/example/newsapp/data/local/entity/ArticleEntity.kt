package com.example.newsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.domain.model.Article
import java.util.Date

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
    val source: String,
    val author: String?,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val savedAt: Date = Date()
) {
    fun toArticle(): Article {
        return Article(
            source = source,
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content,
            isSaved = true
        )
    }
}