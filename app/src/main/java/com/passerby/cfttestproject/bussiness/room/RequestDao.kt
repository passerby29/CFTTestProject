package com.passerby.cfttestproject.bussiness.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RequestDao {

    @Query("select * from Requests order by id desc")
    fun getAllRequests(): LiveData<List<RequestsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun newRequest(item: RequestsEntity)
}