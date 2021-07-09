package kz.smartideagroup.jumys.authorization.manager.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_registration_manager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.authorization.manager.model.request.SignUpManagerRequest
import kz.smartideagroup.jumys.authorization.manager.viewmodel.RegistrationManagerViewModel
import kz.smartideagroup.jumys.common.utils.PUT_PHONE
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class RegistrationManagerFragment : BaseFragment(R.layout.fragment_registration_manager) {

    private lateinit var viewModel: RegistrationManagerViewModel

    private var isValidateName = false
    private var isValidateEmail = false

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
        viewModel.isError.observe(viewLifecycleOwner, {
            setLoading(false)
            errorDialog(getString(R.string.error_no_internet_msg))
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, {
            when (it) {
                true -> navigateTo(R.id.homeManagerFragment)
                false -> {
                    setLoading(false)
                    errorDialog(getString(R.string.error_failed_connection_to_server))
                }
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
            prepareRegistration()
        }
    }

    private fun prepareRegistration() {
        val phone = arguments?.getString(PUT_PHONE) as String
        val name = et_name.text.toString()
        val email = et_email.text.toString()

        isValidateName = if (name.isNotEmpty()) true else {
            et_name.error = getString(R.string.enter_your_name)
            false
        }

        isValidateEmail = if (email.isNotEmpty()) true else {
            et_email.error = getString(R.string.enter_your_email)
            false
        }

        val signUpManagerRequest =
            SignUpManagerRequest(phone = phone, ftoken = "sad", full_name = name, email = email)

        when (isValidateEmail && isValidateName) {
            true -> setRegister(signUpManagerRequest)
        }
    }

    private fun setRegister(signUpManagerRequest: SignUpManagerRequest) {
        setLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.registration(signUpManagerRequest)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[RegistrationManagerViewModel::class.java]
    }

    private fun setLoading(loading: Boolean) {
        loadingView.visibility = if (loading) View.VISIBLE else View.GONE
        btn_next.isEnabled = loading
    }
}