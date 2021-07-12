package kz.smartideagroup.jumys.client.view.support

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_support_client.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class SupportClientFragment : BaseFragment(R.layout.fragment_support_client) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        ll_on_back_pressed.onClick {
            activity?.onBackPressed()
        }
    }

    private fun initObservers() {

    }

    private fun initViewModel() {

    }
}