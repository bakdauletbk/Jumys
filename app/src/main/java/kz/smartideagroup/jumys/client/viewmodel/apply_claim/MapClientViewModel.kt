package kz.smartideagroup.jumys.client.viewmodel.apply_claim

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kz.smartideagroup.jumys.client.model.repository.apply_claim.MapClientRepository
import kz.smartideagroup.jumys.client.model.response.apply_claim.AddressOrderResponse

class MapClientViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MapClientRepository(application)

    val addressOrderList = MutableLiveData<List<AddressOrderResponse>>()

    fun getAddressOrderList() {
        addressOrderList.postValue(repository.getAddressOrderList())
    }
}