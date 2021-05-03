package kz.smartideagroup.jumys.client.viewmodel.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kz.smartideagroup.jumys.client.model.repository.home.HomeClientRepository
import kz.smartideagroup.jumys.client.model.response.home.AdviceResponse
import kz.smartideagroup.jumys.client.model.response.home.CategoryResponse
import kz.smartideagroup.jumys.client.model.response.home.HistoryResponse
import java.lang.Exception

class HomeClientViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "HomeClientViewModel"
    }

    private val repository = HomeClientRepository(application)

    val adviceList = MutableLiveData<List<AdviceResponse>>()
    val historyList = MutableLiveData<List<HistoryResponse>>()
    val categoryList = MutableLiveData<ArrayList<CategoryResponse>>()

    fun getHistory() {
        historyList.postValue(repository.getHistory())
    }

    fun getCategory() {
        categoryList.postValue(repository.getCategory())
    }

    fun getAdvice() {
        adviceList.postValue(repository.getAdvice())
    }

}