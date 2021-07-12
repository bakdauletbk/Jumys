package kz.smartideagroup.jumys.specialist.viewmodel.apply_claim

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kz.smartideagroup.jumys.specialist.model.repository.apply_claim.MapSpecialistRepository
import kz.smartideagroup.jumys.specialist.model.response.apply_claim.AddressOrderResponse

class MapSpecialistViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MapSpecialistRepository(application)

    val addressOrderList = MutableLiveData<List<AddressOrderResponse>>()

    fun getAddressOrderList() {
        addressOrderList.postValue(repository.getAddressOrderList())
    }
}