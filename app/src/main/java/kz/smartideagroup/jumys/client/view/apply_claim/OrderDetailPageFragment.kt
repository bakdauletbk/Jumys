package kz.smartideagroup.jumys.client.view.apply_claim

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import kotlinx.android.synthetic.main.fragment_order_detail_page.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.client.model.response.apply_claim.AddressOrderResponse
import kz.smartideagroup.jumys.common.utils.SERIALIZED_ORDER
import kz.smartideagroup.jumys.common.utils.setImage
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class OrderDetailPageFragment : BaseFragment(R.layout.fragment_order_detail_page) {

    private var order: AddressOrderResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateTo(R.id.orderListFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        setOrderDetailInfo()
        initListeners()
    }

    private fun initListeners() {
        ll_on_back_pressed.onClick {
            navigateTo(R.id.orderListFragment)
        }
    }

    private fun setOrderDetailInfo() {
        order = arguments?.getSerializable(
            SERIALIZED_ORDER
        ) as AddressOrderResponse

        order.let {
            iv_profile_detail_page.setImage(it!!.img.toString())
            tv_name_detail_page.text = it.name
            tv_description.text = it.description
            tv_budget.text = it.price!!.toLong().toString()
            tv_address.text = it.address
        }

        btn_call.onClick {
            call(order!!.number.toString())
        }

    }
}