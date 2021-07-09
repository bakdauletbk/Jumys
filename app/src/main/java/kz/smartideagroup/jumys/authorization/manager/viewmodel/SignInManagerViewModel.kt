package kz.smartideagroup.jumys.authorization.manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.authorization.manager.model.SignInManagerRepository
import kz.smartideagroup.jumys.authorization.manager.model.request.AuthSmsRequest
import kz.smartideagroup.jumys.common.utils.RESPONSE_SUCCESS
import kz.smartideagroup.jumys.common.utils.RESPONSE_UNAUTHORIZED
import kz.smartideagroup.jumys.common.utils.RESPONSE_UPDATE_APP

class SignInManagerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SignInManagerRepository(application)

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