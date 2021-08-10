package kz.smartideagroup.jumys.authorization.client.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_register_client_success.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class RegisterSuccessClientFragment : BaseFragment(R.layout.fragment_register_client_success) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initListeners()
    }

    private fun initListeners() {
        btn_next.onClick {
            setLoading(true)
            navigateTo(R.id.homeClientFragment)
        }
    }
    private fun setLoading(loading: Boolean){
        loadingViews.visibility = if (loading) View.VISIBLE else View.GONE
    }

}