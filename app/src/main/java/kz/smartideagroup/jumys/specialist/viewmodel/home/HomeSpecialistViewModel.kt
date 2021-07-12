package kz.smartideagroup.jumys.specialist.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kz.smartideagroup.jumys.specialist.model.repository.home.HomeSpecialistRepository
import kz.smartideagroup.jumys.specialist.model.response.home.AdviceResponse
import kz.smartideagroup.jumys.specialist.model.response.home.CategoryResponse
import kz.smartideagroup.jumys.specialist.model.response.home.HistoryResponse

class HomeSpecialistViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "HomeClientViewModel"
    }

    private val repository = HomeSpecialistRepository(application)

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