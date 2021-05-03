package kz.smartideagroup.jumys.client.view.apply_claim

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment

class OrderListFragment : BaseFragment(R.layout.fragment_order_list) {

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

    }

}