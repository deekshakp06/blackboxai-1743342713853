package com.example.newsapp.data.mapper

import com.example.newsapp.data.local.entity.ArticleEntity
import com.example.newsapp.data.remote.dto.ArticleDto
import com.example.newsapp.domain.model.Article

fun ArticleDto.toArticle(): Article {
    return Article(
        source = source.name,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        url = url,
        source = source,
        author = author,
        title = title,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}