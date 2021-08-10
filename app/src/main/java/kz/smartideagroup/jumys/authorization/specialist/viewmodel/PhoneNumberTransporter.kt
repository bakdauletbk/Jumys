package kz.smartideagroup.jumys.authorization.specialist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class PhoneNumberTransporter(application: Application): AndroidViewModel(application) {

    val phone: MutableLiveData<String> = MutableLiveData()

    fun setPhone(phone: String){
        this.phone.postValue(phone)
    }

    fun resetPhone(){
        this.phone.postValue(null)
    }

}