package com.passerby.cfttestproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.passerby.cfttestproject.bussiness.BaseResponse
import com.passerby.cfttestproject.bussiness.models.DataModel
import com.passerby.cfttestproject.bussiness.repos.MainRepository
import com.passerby.cfttestproject.bussiness.room.BINAppDB
import com.passerby.cfttestproject.bussiness.room.RequestsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: MainRepository
    val requestList: LiveData<List<RequestsEntity>>
    private val requestDao = BINAppDB.getDatabase(application).getRequestDao()
    var result: MutableLiveData<BaseResponse<DataModel>> = MutableLiveData()

    init {
        repo = MainRepository(requestDao)
        requestList = repo.requestsList
    }

    fun getBIN(bin: String) {
        result.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = repo.getBIN(bin = bin)
                if (response?.code() == 200) {
                    result.value = BaseResponse.Success(response.body())
                } else {
                    result.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: java.lang.Exception) {
                result.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun insert(item: RequestsEntity) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(item)
    }
}