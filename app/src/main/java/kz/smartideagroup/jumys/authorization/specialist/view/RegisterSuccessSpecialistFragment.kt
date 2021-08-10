package kz.smartideagroup.jumys.authorization.specialist.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_register_specialist_success.*
import androidx.activity.addCallback
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class RegisterSuccessSpecialistFragment : BaseFragment(R.layout.fragment_register_specialist_success) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
        requireActivity().onBackPressedDispatcher.addCallback(this){
            navigateTo(R.id.registrationSpecialistFragment)
        }

    }

    private fun lets() {
        initListeners()
    }

    private fun initListeners() {
        btn_next.onClick {
            setLoading(true)
            navigateTo(R.id.homeSpecialistFragment)
        }
    }
    private fun setLoading(loading: Boolean){
        loadingViews.visibility = if (loading) View.VISIBLE else View.GONE
    }

}