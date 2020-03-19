package com.example.nytimesoverview.news.overview.model

import android.app.Application
import androidx.lifecycle.*
import com.example.nytimesoverview.network.*
import com.example.nytimesoverview.database.getDatabase
import com.example.nytimesoverview.repository.NewsRepository
import kotlinx.coroutines.*

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val newsRepository = NewsRepository(getDatabase(application), application)

    enum class NYTimesNewsApiStatus { LOADING, ERROR, DONE }

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _status = MutableLiveData<NYTimesNewsApiStatus>()
    val status: LiveData<NYTimesNewsApiStatus>
        get() = _status

    private val _newsData = MutableLiveData<List<NewsProperties>>()
    val newsData: LiveData<List<NewsProperties>>//newsRepository.newsFromRepo
        get() = _newsData
    //val newsData = newsRepository.newsFromRepo

    private val _navigateToSelectedProperty = MutableLiveData<NewsProperties>()
    val navigateToSelectedProperty: LiveData<NewsProperties>
        get() = _navigateToSelectedProperty

    private val _filterPeriod = MutableLiveData<NewsPeriodFilter>()
    val filterPeriod: LiveData<NewsPeriodFilter>
        get() = _filterPeriod


    init {
        //getArticles(NewsPeriodFilter.THIRTY_DAYS)
        refreshDataFromRepository(NewsPeriodFilter.THIRTY_DAYS)

    }

    private fun refreshDataFromRepository(filter: NewsPeriodFilter) {
        coroutineScope.launch {
            try {
                _status.value =
                    NYTimesNewsApiStatus.LOADING
                _filterPeriod.value = filter
                _newsData.value = newsRepository.refreshNews(filter)
                //newsRepository.refreshNewsData(filter)
                _status.value = NYTimesNewsApiStatus.DONE
            }
            catch (e: Exception) {
                _status.value =
                    NYTimesNewsApiStatus.ERROR
            }
        }
    }

    fun updateFilter(filter: NewsPeriodFilter) {
        refreshDataFromRepository(filter)
    }

    fun displayPropertyDetails(newsProperty: NewsProperties) {
        _navigateToSelectedProperty.value = newsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun onLastDayRadioSelected() {
        refreshDataFromRepository(NewsPeriodFilter.LAST_DAY)
    }

    fun onSevenDaysRadioSelected() {
        refreshDataFromRepository(NewsPeriodFilter.SEVEN_DAYS)
    }

    fun onThirtyDaysRadioSelected() {
        refreshDataFromRepository(NewsPeriodFilter.THIRTY_DAYS)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
