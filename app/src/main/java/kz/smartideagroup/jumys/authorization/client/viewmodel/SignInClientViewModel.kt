package kz.smartideagroup.jumys.authorization.client.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.authorization.client.model.SignInClientRepository
import kz.smartideagroup.jumys.authorization.client.model.request.AuthSmsRequest
import kz.smartideagroup.jumys.common.utils.RESPONSE_SUCCESS
import kz.smartideagroup.jumys.common.utils.RESPONSE_UNAUTHORIZED
import kz.smartideagroup.jumys.common.utils.RESPONSE_UPDATE_APP

class SignInClientViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SignInClientRepository(application)

    val isError = MutableLiveData<String>()
    val isSuccess = MutableLiveData<Boolean>()
    val isUpdateApp = MutableLiveData<Boolean>()
    val isUnAuthorized = MutableLiveData<Boolean>()

    suspend fun sendSms(authSmsRequest: AuthSmsRequest) {
        viewModelScope.launch {
            try {
                val response = repository.sendSms(authSmsRequest)
                when (response.code()) {
                    RESPONSE_SUCCESS -> isSuccess.postValue(true)
                    RESPONSE_UNAUTHORIZED -> isUnAuthorized.postValue(true)
                    RESPONSE_UPDATE_APP -> isUpdateApp.postValue(true)
                    else -> isSuccess.postValue(false)
                }
            } catch (e: Exception) {
                isError.postValue("")
            }
        }
    }

}