package kz.smartideagroup.jumys.manager.view.home

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment

class HomeManagerFragment : BaseFragment(R.layout.fragment_home_manager) {

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

    }

}