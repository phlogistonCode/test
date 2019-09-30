package com.appricot.test.db

import androidx.room.*
import com.appricot.test.list.models.RequestModel

@Dao
interface RequestModelDao {

    @Query("SELECT * FROM requestModel")
    suspend fun getAll(): List<RequestModel>

    @Query("SELECT * FROM requestModel WHERE id = :id")
    fun getById(id: Int): RequestModel

    @Insert
    fun insert(requestModel: RequestModel)

    @Update
    fun update(requestModel: RequestModel)

    @Delete
    fun delete(requestModel: RequestModel)

    @Query("DELETE FROM requestModel")
    suspend fun deleteAll()

    @Insert
    suspend fun insertList(requestModels: List<RequestModel>)

}