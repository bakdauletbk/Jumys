package kz.smartideagroup.jumys.authorization.specialist.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_sign_in_specialist.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.authorization.specialist.viewmodel.SignInSpecialistViewModel
import kz.smartideagroup.jumys.authorization.client.model.request.AuthSmsRequest
import kz.smartideagroup.jumys.common.helpers.TextUtils
import kz.smartideagroup.jumys.common.helpers.Validators
import kz.smartideagroup.jumys.common.utils.PUT_PHONE
import kz.smartideagroup.jumys.common.utils.PUT_PHONE_NUMBER
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class SignInSpecialistFragment : BaseFragment(R.layout.fragment_sign_in_specialist) {

    private lateinit var viewModel: SignInSpecialistViewModel
    val bundle = Bundle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateTo(R.id.roleFragment)
        }
    }

    private fun lets() {
        initViewModel()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.isError.observe(viewLifecycleOwner, {
            setLoading(false)
            errorDialog(getString(R.string.error_no_internet_msg))
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, {
            when (it) {
                true -> navigateToBundle(R.id.action_signInSpecialistFragment_to_smsCodeSpecialistFragment,
                    bundle)
                false -> {
                    setLoading(false)
                    errorDialog(getString(R.string.error_failed_connection_to_server))
                }
            }
        })
        viewModel.isUpdateApp.observe(viewLifecycleOwner, {
            when (it) {
                true ->
                    showAlertDialog(
                        requireContext(),
                        getString(R.string.our_application_has_been_updated_please_update)
                    )
            }
        })
        viewModel.isUnAuthorized.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    setLoading(false)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.you_are_logged_in_under_your_account_on_another_device),
                        Toast.LENGTH_LONG
                    ).show()
                    navigateTo(R.id.roleFragment)
                }
            }
        })
    }

    private fun setLoading(loading: Boolean) {
        loadingView.visibility = if (loading) View.VISIBLE else View.GONE
        et_phone.isEnabled = loading
        btn_next.isEnabled = loading
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[SignInSpecialistViewModel::class.java]
    }

    private fun initListeners() {
        btn_next.onClick {
            prepareSms()
        }
        iv_back.onClick {
            navigateTo(R.id.roleFragment)
        }
    }

    private fun prepareSms() {
        val phone = et_phone.text.toString()
        when (Validators.validateLogin(phone)) {
            true -> {
                val authSmsRequest = AuthSmsRequest(TextUtils.textToNumberFormat(phone))
                bundle.putString(PUT_PHONE, TextUtils.textToNumberFormat(phone))
                bundle.putString(PUT_PHONE_NUMBER,phone)
                sendSms(authSmsRequest)
            }
            false -> et_phone.error = getString(R.string.enter_the_number)
        }
    }

    private fun sendSms(authSmsRequest: AuthSmsRequest) {
        setLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.sendSms(authSmsRequest)
        }
    }

}