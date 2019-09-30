package com.appricot.test.di

import android.content.Context
import androidx.room.Room
import com.appricot.test.db.RequestModelDao
import com.appricot.test.db.RequestModelDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDb(context: Context): RequestModelDao {
        return Room.databaseBuilder(context, RequestModelDataBase::class.java, "request_model.db").build().requestModelDao()
    }

}