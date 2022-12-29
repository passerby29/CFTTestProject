package com.passerby.cfttestproject.bussiness.repos

import androidx.lifecycle.LiveData
import com.passerby.cfttestproject.bussiness.BINApi
import com.passerby.cfttestproject.bussiness.models.DataModel
import com.passerby.cfttestproject.bussiness.room.RequestDao
import com.passerby.cfttestproject.bussiness.room.RequestsEntity
import retrofit2.Response

class MainRepository(private val requestDao: RequestDao) {

    val requestsList: LiveData<List<RequestsEntity>> = requestDao.getAllRequests()

    suspend fun getBIN(bin: String): Response<DataModel>? {
        return BINApi.getApi()?.getBINData(bin = bin)
    }

    suspend fun insert(item: RequestsEntity) {
        requestDao.newRequest(item)
    }
}