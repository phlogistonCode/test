package com.appricot.test.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appricot.test.list.models.RequestModel

@Database(entities = [RequestModel::class], version = 1)
abstract class RequestModelDataBase : RoomDatabase() {

    abstract fun requestModelDao(): RequestModelDao

//    companion object {
//        private var INSTANCE: RequestModelDataBase? = null
//
//        fun getInstance(context: Context): RequestModelDataBase? {
//            if (INSTANCE == null) {
//                synchronized(RequestModelDataBase::class) {
//                    INSTANCE = Room.databaseBuilder(context.applicationContext,
//                        RequestModelDataBase::class.java, "request_model.db")
//                        .build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyInstance() {
//            INSTANCE = null
//        }
//    }
}