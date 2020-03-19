package com.example.nytimesoverview.news.details

import android.app.Application
import androidx.lifecycle.*
import com.example.nytimesoverview.database.getDatabase
import com.example.nytimesoverview.news.overview.model.NewsComment
import com.example.nytimesoverview.news.overview.model.NewsProperties
import com.example.nytimesoverview.repository.NewsRepository

class NewsDetailsViewModel(newsProperties: NewsProperties, application: Application) : AndroidViewModel(application) {

    private val newsRepository = NewsRepository(getDatabase(application), application)

    private val _selectedProperty = MutableLiveData<NewsProperties>()
    val selectedProperty: LiveData<NewsProperties>
        get() = _selectedProperty

    val commentsData: LiveData<List<NewsComment>> = newsRepository.getCommentsForNews(newsProperties.id)

    init {
        _selectedProperty.value = newsProperties
    }

    fun onAddComment(comment: String) {
        newsRepository.addCommentForNews(selectedProperty.value!!, NewsComment(null, selectedProperty.value!!.id, comment))
    }
}
