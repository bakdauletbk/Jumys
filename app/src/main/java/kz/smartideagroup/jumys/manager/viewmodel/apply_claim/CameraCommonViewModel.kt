package kz.smartideagroup.jumys.manager.viewmodel.apply_claim

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CameraCommonViewModel(application: Application) : AndroidViewModel(application) {

    val mediaList = MutableLiveData<ArrayList<String>>()
    val mediaArrayList = ArrayList<String>()
    var isLastItem: Boolean = false

    fun setMediaArrayList(media: String) {
        when (isLastItem) {
            true -> {
                this.mediaArrayList.add(media)
                this.mediaList.postValue(mediaArrayList)
            }
            false -> {
                this.mediaArrayList.add(media)
                this.mediaArrayList.add("")
                this.mediaList.postValue(mediaArrayList)
                isLastItem = true
            }
        }
    }

}