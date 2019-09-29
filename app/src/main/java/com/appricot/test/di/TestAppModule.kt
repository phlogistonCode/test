package com.appricot.test.di

import android.app.Application
import android.content.Context
import com.appricot.test.TestApp
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class TestAppModule(val app: TestApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    fun provideCoroutineContextIO(): CoroutineContext = IO

}