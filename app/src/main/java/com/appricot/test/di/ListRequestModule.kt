package com.appricot.test.di

import com.appricot.test.api.GlabstoreApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ListRequestModule {

    @Provides
    @Singleton
    fun provideGalbstoreApi(retrofit: Retrofit): GlabstoreApi = retrofit.create(GlabstoreApi::class.java)

}