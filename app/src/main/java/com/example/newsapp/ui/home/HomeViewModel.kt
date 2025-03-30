package com.example.newsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsFlow = MutableStateFlow<Resource<List<Article>>>(Resource.Loading())
    val newsFlow: StateFlow<Resource<List<Article>>> = _newsFlow.asStateFlow()

    private var currentCategory: Category? = null

    init {
        getTopHeadlines()
    }

    fun getTopHeadlines(category: String? = null) {
        currentCategory = category?.let { Category.fromValue(it) }
        viewModelScope.launch {
            _newsFlow.value = Resource.Loading()
            _newsFlow.value = repository.getTopHeadlines(category)
        }
    }

    fun getCurrentCategory(): Category? = currentCategory

    fun refreshNews() {
        getTopHeadlines(currentCategory)
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            repository.saveArticle(article)
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }
}