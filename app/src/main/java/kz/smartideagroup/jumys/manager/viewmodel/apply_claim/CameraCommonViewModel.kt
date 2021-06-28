package kz.smartideagroup.jumys.manager.viewmodel.apply_claim

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CameraCommonViewModel(application: Application) : AndroidViewModel(application) {

    val mediaList = MutableLiveData<ArrayList<String>>()
    val mediaArrayList = ArrayList<String>()

    fun setMediaArrayList(media: String) {
        this.mediaArrayList.add(media)
        this.mediaList.postValue(mediaArrayList)
    }

}