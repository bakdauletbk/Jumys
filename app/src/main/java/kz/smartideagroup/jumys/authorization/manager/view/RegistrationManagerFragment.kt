package kz.smartideagroup.jumys.authorization.manager.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_registration_manager.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class RegistrationManagerFragment : BaseFragment(R.layout.fragment_registration_manager) {

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

        btn_next.onClick {
            navigateTo(R.id.homeManagerFragment)
        }

    }

    private fun initViewModel() {

    }
}