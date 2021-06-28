package kz.smartideagroup.jumys.manager.viewmodel.apply_claim

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CameraCommonViewModel(application: Application) : AndroidViewModel(application) {

    val mediaList = MutableLiveData<ArrayList<String>>()
    private val mediaArrayList = ArrayList<String>()
    val sum = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()

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