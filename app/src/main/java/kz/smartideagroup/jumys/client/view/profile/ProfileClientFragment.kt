package kz.smartideagroup.jumys.client.view.profile

import android.os.Bundle
import androidx.activity.addCallback
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment

class ProfileClientFragment : BaseFragment(R.layout.fragment_profile_client) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }
    }

}