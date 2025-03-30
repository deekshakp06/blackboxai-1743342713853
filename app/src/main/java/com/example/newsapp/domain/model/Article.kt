package com.example.newsapp.domain.model

data class Article(
    val source: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val isSaved: Boolean = false
) {
    companion object {
        fun empty(): Article {
            return Article(
                source = "",
                author = "",
                title = "",
                description = "",
                url = "",
                urlToImage = "",
                publishedAt = "",
                content = ""
            )
        }
    }
}