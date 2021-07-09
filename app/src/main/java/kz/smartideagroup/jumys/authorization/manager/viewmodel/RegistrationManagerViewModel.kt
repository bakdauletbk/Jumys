package kz.smartideagroup.jumys.authorization.manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.authorization.manager.model.RegistrationManagerRepository
import kz.smartideagroup.jumys.authorization.manager.model.request.SignUpManagerRequest
import kz.smartideagroup.jumys.common.utils.RESPONSE_SUCCESS
import kz.smartideagroup.jumys.common.utils.RESPONSE_UNAUTHORIZED
import kz.smartideagroup.jumys.common.utils.RESPONSE_UPDATE_APP

class RegistrationManagerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RegistrationManagerRepository(application)

    val isError = MutableLiveData<String>()
    val isSuccess = MutableLiveData<Boolean>()
    val isUpdateApp = MutableLiveData<Boolean>()
    val isUnAuthorized = MutableLiveData<Boolean>()

    suspend fun registration(signUpManagerRequest: SignUpManagerRequest) {
        viewModelScope.launch {
            try {
                val response = repository.registration(signUpManagerRequest)
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
                isError.postValue("")
            }
        }
    }
}