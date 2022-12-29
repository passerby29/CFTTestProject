package com.passerby.cfttestproject.bussiness

import com.passerby.cfttestproject.bussiness.models.DataModel
import retrofit2.Response
import retrofit2.http.*

interface BINApi {
    @POST("{bin}")
    suspend fun getBINData(@Path("bin") bin: String): Response<DataModel>

    companion object {
        fun getApi(): BINApi? {
            return ApiClient.client?.create(BINApi::class.java)
        }
    }
}