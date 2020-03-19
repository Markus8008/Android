package com.example.nytimesoverview.injections.component

import com.example.nytimesoverview.database.NYTimesDatabase
import com.example.nytimesoverview.injections.module.ContextModule
import com.example.nytimesoverview.injections.module.DatabaseModule
import com.example.nytimesoverview.injections.module.NetworkModule
import com.example.nytimesoverview.repository.NewsRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class,
                        DatabaseModule::class])
interface NewsRepositoryInjector {
    /**
     * Injects required dependencies into the specified Repository.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(newsRepository: NewsRepository)

    @Component.Builder
    interface Builder {
        fun build(): NewsRepositoryInjector
        fun databaseModule(databaseModule: DatabaseModule): Builder
        //fun contextModule(contextModule: ContextModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder
    }

}