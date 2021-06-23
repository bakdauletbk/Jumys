package kz.smartideagroup.jumys.authorization.manager.view

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_sms_code_manager.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class SmsCodeManagerFragment : BaseFragment(R.layout.fragment_sms_code_manager) {

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
            view?.let { it1 ->
                Navigation.findNavController(it1).navigate(R.id.action_smsCodeFragment_to_registrationFragment)
            }
        }
    }

    private fun initViewModel() {

    }

}