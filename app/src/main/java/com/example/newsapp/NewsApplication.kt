package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Koin DI
        startKoin {
            androidContext(this@NewsApplication)
            modules(AppModule)
        }
        
        // Initialize other app components
        initializeNotificationChannels()
    }
    
    private fun initializeNotificationChannels() {
        // Notification channel setup will be implemented later
    }
}