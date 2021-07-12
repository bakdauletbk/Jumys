package kz.smartideagroup.jumys

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import kotlinx.android.synthetic.main.fragment_role.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class RoleFragment : BaseFragment(R.layout.fragment_role) {

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
        ll_customer.onClick {
            navigateTo(R.id.action_roleFragment_to_signInClientFragment)
        }

        ll_specialist.onClick {
            navigateTo(R.id.action_roleFragment_to_signInSpecialistFragment)
        }
    }

}