package kz.smartideagroup.jumys.client.view.profile

import android.os.Bundle
import android.view.View
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment

class RequestCallClientFragment : BaseFragment(R.layout.fragment_request_call_client) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initListeners()
        initObservers()
    }

    private fun initViewModel() {

    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }

}