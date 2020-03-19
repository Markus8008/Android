package com.example.nytimesoverview.injections.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
object ContextModule: Application() {

    @Provides
    //@MyApplicationScope
    internal fun provideContext(): Context {
        return this.applicationContext
    }
}