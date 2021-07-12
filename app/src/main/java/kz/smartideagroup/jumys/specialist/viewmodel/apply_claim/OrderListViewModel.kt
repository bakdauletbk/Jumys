package kz.smartideagroup.jumys.specialist.viewmodel.apply_claim

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kz.smartideagroup.jumys.specialist.model.repository.apply_claim.OrderListRepository
import kz.smartideagroup.jumys.specialist.model.response.apply_claim.AddressOrderResponse

class OrderListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = OrderListRepository(application)
    val orderList = MutableLiveData<List<AddressOrderResponse>>()

    fun getOrderList(){
        orderList.postValue(repository.getOrderList())
    }

}