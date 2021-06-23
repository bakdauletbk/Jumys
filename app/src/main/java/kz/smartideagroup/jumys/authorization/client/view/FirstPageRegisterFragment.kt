package kz.smartideagroup.jumys.authorization.client.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.first_page_register.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class FirstPageRegisterFragment : BaseFragment(R.layout.first_page_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initListeners()
    }

    private fun initListeners() {
        btn_next.onClick {
            navigateTo(R.id.homeClientFragment)
        }
    }

}