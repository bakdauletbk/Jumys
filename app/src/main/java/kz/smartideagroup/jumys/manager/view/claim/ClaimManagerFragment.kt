package kz.smartideagroup.jumys.manager.view.claim

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment

class ClaimManagerFragment : BaseFragment(R.layout.fragment_claim_manager) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initListeners()
        initObservers()
    }

    private fun initObservers() {

    }

    private fun initListeners() {

    }

    private fun initViewModel() {

    }

}