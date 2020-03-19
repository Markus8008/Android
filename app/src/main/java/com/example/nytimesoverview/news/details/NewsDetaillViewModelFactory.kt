package com.example.nytimesoverview.news.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nytimesoverview.news.overview.model.NewsProperties

class NewsDetaillViewModelFactory (

    private val newsProperty: NewsProperties,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsDetailsViewModel::class.java)) {
                return NewsDetailsViewModel(newsProperty, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}