package kz.smartideagroup.jumys.manager.view.profile

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import kotlinx.android.synthetic.main.fragment_profile_manager.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class ProfileManagerFragment : BaseFragment(R.layout.fragment_profile_manager) {

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
        initListeners()
    }

    private fun initListeners() {
        btn_log_out_manager.onClick {
            navigateTo(R.id.roleFragment)
        }
    }

}