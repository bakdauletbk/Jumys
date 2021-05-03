package kz.smartideagroup.jumys.client.view.apply_claim

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_order_list.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.client.model.response.apply_claim.AddressOrderResponse
import kz.smartideagroup.jumys.client.view.apply_claim.adapter.OrdersAdapter
import kz.smartideagroup.jumys.client.view.home.adapter.CategoryAdapter
import kz.smartideagroup.jumys.client.viewmodel.apply_claim.OrderListViewModel
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class OrderListFragment : BaseFragment(R.layout.fragment_order_list) {

    private lateinit var viewModel: OrderListViewModel

    private val orderAdapter: OrdersAdapter =
        OrdersAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateTo(R.id.mapClientFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        getOrderList()
        initRecyclerView()
        initListeners()
        initObservers()
    }

    private fun getOrderList() {
        viewModel.getOrderList()
    }

    private fun initRecyclerView() {
        rv_orders.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[OrderListViewModel::class.java]
    }

    private fun initListeners() {
        btn_map.onClick {
            navigateTo(R.id.mapClientFragment)
        }
        ll_on_back_pressed.onClick {
            navigateTo(R.id.mapClientFragment)
        }
    }

    fun onClickItem(addressOrderResponse: AddressOrderResponse){

    }

    private fun initObservers() {
        viewModel.orderList.observe(viewLifecycleOwner, {
            setOrderList(it)
        })
    }

    private fun setOrderList(it: List<AddressOrderResponse>?) {
        orderAdapter.addLoans(it!!)
    }

}