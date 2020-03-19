package com.example.nytimesoverview.base

import com.example.nytimesoverview.injections.component.DaggerNewsRepositoryInjector
import com.example.nytimesoverview.injections.component.NewsRepositoryInjector
import com.example.nytimesoverview.injections.module.NetworkModule
import com.example.nytimesoverview.repository.NewsRepository

abstract class BaseNewsRepository {

    private val injector: NewsRepositoryInjector = DaggerNewsRepositoryInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    /*init {
        inject()
    }

    private fun inject() {
        when(this) {
            is NewsRepository -> injector.inject(this)
        }
    }*/
}