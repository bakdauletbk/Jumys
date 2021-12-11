package kz.smartideagroup.jumys.authorization.client.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.authorization.client.model.RegistrationClientRepository
import kz.smartideagroup.jumys.authorization.client.model.request.SignUpClientRequest
import kz.smartideagroup.jumys.common.utils.RESPONSE_SUCCESS
import kz.smartideagroup.jumys.common.utils.RESPONSE_UNAUTHORIZED
import kz.smartideagroup.jumys.common.utils.RESPONSE_UPDATE_APP

class RegistrationClientViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RegistrationClientRepository(application)

    val isError = MutableLiveData<String>()
    val isSuccess = MutableLiveData<Boolean>()
    val isUpdateApp = MutableLiveData<Boolean>()
    val isUnAuthorized = MutableLiveData<Boolean>()

    suspend fun registration(signUpClientRequest: SignUpClientRequest) {
        viewModelScope.launch {
            try {
                val response = repository.registration(signUpClientRequest)
                Log.d("exception response", response.toString())
                when (response.code()) {
                    RESPONSE_SUCCESS -> {
                        isSuccess.postValue(true)
                        response.body()?.user?.let { repository.saveUser(it) }
                    }
                    RESPONSE_UNAUTHORIZED -> isUnAuthorized.postValue(true)
                    RESPONSE_UPDATE_APP -> isUpdateApp.postValue(true)
                    else -> isSuccess.postValue(false)
                }
            } catch (e: Exception) {
                Log.e("exception", e.toString())
                isError.postValue("")
            }
        }
    }
}