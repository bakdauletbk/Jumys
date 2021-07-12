package kz.smartideagroup.jumys.authorization.client.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.authorization.client.model.SmsCodeClientRepository
import kz.smartideagroup.jumys.authorization.client.model.request.VerificationRequest
import kz.smartideagroup.jumys.common.utils.RESPONSE_SUCCESS
import kz.smartideagroup.jumys.common.utils.RESPONSE_UNAUTHORIZED
import kz.smartideagroup.jumys.common.utils.RESPONSE_UPDATE_APP

class SmsCodeClientViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SmsCodeClientRepository(application)

    val isError = MutableLiveData<String>()
    val isSuccess = MutableLiveData<Boolean>()

    val isUpdateApp = MutableLiveData<Boolean>()
    val isUnAuthorized = MutableLiveData<Boolean>()

    suspend fun verification(verificationRequest: VerificationRequest) {
        viewModelScope.launch {
            try {
                val response = repository.verification(verificationRequest)
                when (response.code()) {
                    RESPONSE_SUCCESS -> {
                        isSuccess.postValue(true)
                        when (response.body() != null) {
                            true -> {
                                isSuccess.postValue(true)
                                response.body()?.user?.let { repository.saveUserInfo(it) }
                            }
                            false -> isSuccess.postValue(false)
                        }
                    }
                    RESPONSE_UNAUTHORIZED -> isUnAuthorized.postValue(true)
                    RESPONSE_UPDATE_APP -> isUpdateApp.postValue(true)
                    else -> isError.postValue("")
                }
            } catch (e: Exception) {
                isError.postValue("")
            }
        }
    }

}