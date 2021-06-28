package kz.smartideagroup.jumys.manager.viewmodel.apply_claim

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CameraCommonViewModel(application: Application) : AndroidViewModel(application) {

    val mediaList = MutableLiveData<ArrayList<String>>()
    private val mediaArrayList = ArrayList<String>()

    fun setMediaArrayList(media: String) {
        this.mediaArrayList.add(media)
        this.mediaList.postValue(mediaArrayList)
    }

    fun removeItem(position: Int) {
        this.mediaArrayList.removeAt(position)
        this.mediaList.postValue(mediaArrayList)
    }

}