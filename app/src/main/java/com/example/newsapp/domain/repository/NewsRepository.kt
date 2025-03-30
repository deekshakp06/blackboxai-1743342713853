package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(
        category: String? = null,
        page: Int = 1,
        pageSize: Int = 20
    ): Resource<List<Article>>

    suspend fun searchNews(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): Resource<List<Article>>

    suspend fun saveArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    fun getSavedArticles(): Flow<List<Article>>
    suspend fun getArticleByUrl(url: String): Article?
}