package kz.smartideagroup.jumys.specialist.view.profile

import android.os.Bundle
import android.view.View
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment

class RequestCallSpecialistFragment : BaseFragment(R.layout.fragment_request_call_specialist) {

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