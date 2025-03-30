package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.remote.NewsApiClient
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApiClient(): NewsApiClient {
        return NewsApiClient
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase {
        return NewsDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        api: NewsApiClient,
        db: NewsDatabase
    ): NewsRepository {
        return NewsRepositoryImpl(api, db)
    }
}