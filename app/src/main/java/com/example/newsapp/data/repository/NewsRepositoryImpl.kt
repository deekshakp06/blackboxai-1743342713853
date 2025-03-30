package com.example.newsapp.data.repository

import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.mapper.toArticle
import com.example.newsapp.data.mapper.toArticleEntity
import com.example.newsapp.data.remote.NewsApiClient
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApiClient,
    private val db: NewsDatabase
) : NewsRepository {

    override suspend fun getTopHeadlines(
        category: String?,
        page: Int,
        pageSize: Int
    ): Resource<List<Article>> {
        return try {
            val response = api.apiService.getTopHeadlines(
                category = category,
                page = page,
                pageSize = pageSize
            )
            Resource.Success(response.articles.map { it.toArticle() })
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun searchNews(
        query: String,
        page: Int,
        pageSize: Int
    ): Resource<List<Article>> {
        return try {
            val response = api.apiService.searchNews(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Resource.Success(response.articles.map { it.toArticle() })
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun saveArticle(article: Article) {
        db.articleDao().insertArticle(article.toArticleEntity())
    }

    override suspend fun deleteArticle(article: Article) {
        db.articleDao().deleteArticle(article.toArticleEntity())
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return db.articleDao().getSavedArticles().map { articles ->
            articles.map { it.toArticle() }
        }
    }

    override suspend fun getArticleByUrl(url: String): Article? {
        return db.articleDao().getArticleByUrl(url)?.toArticle()
    }
}