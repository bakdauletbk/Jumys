package kz.smartideagroup.jumys.authorization.client.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_sms_code_client.*
import kotlinx.android.synthetic.main.fragment_sms_code_client.btn_next
import kotlinx.android.synthetic.main.fragment_sms_code_client.codeInputView
import kotlinx.android.synthetic.main.fragment_sms_code_client.iv_close
import kotlinx.android.synthetic.main.fragment_sms_code_client.loadingView
import kotlinx.android.synthetic.main.fragment_sms_code_specialist.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.authorization.client.model.request.VerificationRequest
import kz.smartideagroup.jumys.authorization.client.viewmodel.SmsCodeClientViewModel
import kz.smartideagroup.jumys.common.helpers.Validators
import kz.smartideagroup.jumys.common.utils.PUT_PHONE
import kz.smartideagroup.jumys.common.utils.PUT_PHONE_NUMBER
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class SmsCodeClientFragment : BaseFragment(R.layout.fragment_sms_code_client) {

    private lateinit var viewModel: SmsCodeClientViewModel
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateTo(R.id.signInClientFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        setNumber()
        initListeners()
        initObservers()
    }

    @SuppressLint("SetTextI18n")
    private fun setNumber() {
        val phone = arguments?.getString(PUT_PHONE_NUMBER) as String
        text_view.text = getString(R.string.send_sms_number_phone) + " " + phone
    }

    private fun initObservers() {
        viewModel.isError.observe(viewLifecycleOwner, {
            setLoading(false)
            errorDialog(getString(R.string.error_no_internet_msg))
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, {
            when (it) {
                true -> navigateTo(R.id.homeClientFragment)
                false -> navigateToBundle(R.id.action_smsCodeClientFragment_to_registrationClientFragment,
                    bundle)
            }
        })
        viewModel.isUpdateApp.observe(viewLifecycleOwner, {
            when (it) {
                true -> showAlertDialog(
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

    private fun initListeners() {
        btn_next.onClick {
            prepareLogin()
        }
        iv_close.onClick {
            navigateTo(R.id.roleFragment)
        }
    }

    private fun prepareLogin() {
        val phone = arguments?.getString(PUT_PHONE) as String
        bundle.putString(PUT_PHONE, phone)
        val smsCode = codeInputView.code.toString()
        val verificationRequest = VerificationRequest(phone = phone,
            activationcode = smsCode,
            role = getString(R.string.client))
        when (Validators.validateSMS(smsCode)) {
            true -> sendVerification(verificationRequest)
            false -> codeInputView.error = getString(R.string.enter_sms)
        }
    }

    private fun sendVerification(verificationRequest: VerificationRequest) {
        setLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.verification(verificationRequest)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[SmsCodeClientViewModel::class.java]
    }

    private fun setLoading(loading: Boolean) {
        loadingView.visibility = if (loading) View.VISIBLE else View.GONE
        btn_next.isEnabled = loading
    }

}