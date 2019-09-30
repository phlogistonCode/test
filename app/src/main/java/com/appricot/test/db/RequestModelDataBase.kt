package com.appricot.test.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appricot.test.list.models.RequestModel

@Database(entities = [RequestModel::class], version = 1)
abstract class RequestModelDataBase : RoomDatabase() {

    abstract fun requestModelDao(): RequestModelDao
}