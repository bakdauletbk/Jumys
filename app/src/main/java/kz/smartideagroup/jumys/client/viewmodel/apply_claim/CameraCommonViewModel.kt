package kz.smartideagroup.jumys.client.viewmodel.apply_claim

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.common.utils.RESPONSE_SUCCESS
import kz.smartideagroup.jumys.client.model.repository.apply_claim.CameraCommonRepository
import kz.smartideagroup.jumys.client.model.request.apply_claim.RequestWorkUpload

class CameraCommonViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CameraCommonRepository(application)

    val mediaList = MutableLiveData<ArrayList<String>>()
    private val mediaArrayList = ArrayList<String>()
    val sum = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val videoBase = MutableLiveData<String>()

    val isError = MutableLiveData<String>()
    val isSuccess = MutableLiveData<Boolean>()

    suspend fun setVideoUpload(requestWorkUpload: RequestWorkUpload) {
        viewModelScope.launch {
            try {
                val response = repository.senVideoUpload(requestWorkUpload)
                when (response.code()) {
                    RESPONSE_SUCCESS -> isSuccess.postValue(true)
                    else -> isSuccess.postValue(false)
                }
            } catch (e: Exception) {
                isError.postValue("")
            }
        }
    }

    fun setVideoBase(videoBase: String) {
        this.videoBase.postValue(videoBase)
    }

    fun setSum(sum: String) {
        this.sum.postValue(sum)
    }

    fun setPhoneNumber(number: String) {
        this.phoneNumber.postValue(number)
    }

    fun setMediaArrayList(media: String) {
        this.mediaArrayList.add(media)
        this.mediaList.postValue(mediaArrayList)
    }

    fun removeItem(position: Int) {
        this.mediaArrayList.removeAt(position)
        this.mediaList.postValue(mediaArrayList)
    }

}