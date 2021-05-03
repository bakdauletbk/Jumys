package kz.smartideagroup.jumys.client.view.home

import android.os.Bundle
import androidx.activity.addCallback
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment

class HomeClientFragment  : BaseFragment(R.layout.fragment_home_client){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }
    }

}