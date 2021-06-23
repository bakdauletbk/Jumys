package kz.smartideagroup.jumys.authorization.client.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.second_page_register.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class SecondPageRegisterFragment : BaseFragment(R.layout.second_page_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initListeners()
    }

    private fun initListeners() {
        too.setOnClickListener {
            bin.visibility = View.VISIBLE
            enter_bin.visibility = View.VISIBLE
            image_view2.visibility = View.VISIBLE
            line15.visibility = View.VISIBLE
            image_view.visibility = View.GONE
            line14.visibility = View.GONE
            register_number.visibility = View.GONE
            line12.visibility = View.GONE
        }

        ip.setOnClickListener {
            bin.visibility = View.GONE
            enter_bin.visibility = View.GONE
            image_view2.visibility = View.GONE
            line15.visibility = View.GONE
            image_view.visibility = View.VISIBLE
            line14.visibility = View.VISIBLE
            register_number.visibility = View.VISIBLE
            line12.visibility = View.VISIBLE
        }

        btn_next.onClick {
            navigateTo(R.id.homeClientFragment)
        }

    }

}