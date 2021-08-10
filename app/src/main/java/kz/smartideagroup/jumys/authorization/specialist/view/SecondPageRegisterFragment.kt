package kz.smartideagroup.jumys.authorization.specialist.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.second_page_register.*
import kotlinx.android.synthetic.main.second_page_register.btn_next
import kotlinx.android.synthetic.main.second_page_register.et_email
import kotlinx.android.synthetic.main.second_page_register.et_fatherland
import kotlinx.android.synthetic.main.second_page_register.et_iin
import kotlinx.android.synthetic.main.second_page_register.et_month
import kotlinx.android.synthetic.main.second_page_register.loadingViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.authorization.specialist.model.request.SignUpSpecialistRequest
import kz.smartideagroup.jumys.authorization.specialist.viewmodel.PhoneNumberTransporter
import kz.smartideagroup.jumys.authorization.specialist.viewmodel.SecondPageViewModel
import kz.smartideagroup.jumys.common.helpers.*
import kz.smartideagroup.jumys.common.utils.*
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class SecondPageRegisterFragment : BaseFragment(R.layout.second_page_register) {
    private lateinit var phoneNumberTransporter: PhoneNumberTransporter
    private lateinit var viewModel: SecondPageViewModel
    private var type = ONE_STR

    private var isValidateName = false
    private var isValidateSureName = false
    private var isValidateFatherLand = false
    private var isValidateDays = false
    private var isValidateMonth = false
    private var isValidateYears = false
    private var isValidateIin = false
    private var isValidateBin = false
    private var isValidateRegisterNumber = false
    private var isValidateEmail = false


    private var phone = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
        requireActivity().onBackPressedDispatcher.addCallback(this){
            navigateTo(R.id.smsCodeSpecialistFragment)
        }
    }

    private fun lets() {
        initViewModel()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        phoneNumberTransporter.phone.observe(viewLifecycleOwner,{
            this.phone = it
        })
        viewModel.isError.observe(viewLifecycleOwner, {
            setLoading(false)
            errorDialog(getString(R.string.error_no_internet_msg))
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, {
            when (it) {
                true -> navigateTo(R.id.registerSuccessSpecialist)
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
        loadingViews.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[SecondPageViewModel::class.java]
        phoneNumberTransporter =
            ViewModelProvider(requireActivity())[PhoneNumberTransporter::class.java]
    }

    private fun initListeners() {
        btn_next.onClick {
            prepareRegister()
        }
        ip.onClick {
            ll_too.visibility = View.GONE
            ll_ip.visibility = View.VISIBLE
            type = ONE_STR
        }

        too.onClick {
            ll_ip.visibility = View.GONE
            ll_too.visibility = View.VISIBLE
            type = TWO_STR
        }

    }


    private fun prepareRegister() {
        val name = edit_name.text.toString()
        val surName = surname_name.text.toString()
        val fatherland = et_fatherland.text.toString()
        val day = et_day.text.toString()
        val month = et_month.text.toString()
        val year = et_year.text.toString()
        val email = et_email.text.toString()
        val iin = et_iin.text.toString()
        val bin = et_bin.text.toString()
        val registerNumber = register_number.text.toString()

        isValidateDays = et_day.validateDays(day,getString(R.string.validate_day))
        isValidateYears = et_year.validateYear(year,getString(R.string.validate_year))
        isValidateMonth = et_month.validateMonth(month,getString(R.string.validate_month))

        isValidateName = validateString(name, edit_name, getString(R.string.enter_your_name))
        isValidateSureName =
            validateString(surName, surname_name, getString(R.string.enter_your_sure_name))
        isValidateFatherLand =
            validateString(fatherland, et_fatherland, getString(R.string.enter_your_father))
        isValidateEmail = et_email.validateEmail(email,getString(R.string.enter_your_email))
        isValidateIin = et_iin.validateIin(iin, getString(R.string.enter_iin))
        isValidateBin = et_bin.validateBin(bin,getString(R.string.enter_your_bin))
        isValidateRegisterNumber = register_number.validateRegisterNumber(registerNumber,getString(R.string.enter_the_number))
        if(type == TWO_STR) {
            isValidateRegisterNumber = true
            isValidateIin = true
        }else isValidateBin = true
        when(isValidateName && isValidateSureName && isValidateFatherLand && isValidateIin && isValidateDays
                && isValidateMonth && isValidateYears && isValidateEmail
                && isValidateRegisterNumber  && isValidateBin)  {
            true -> {
                val birthday = "$year-$month-$day"
                val signUpClientRequest = SignUpSpecialistRequest(
                    phone = phone,
                    ftoken = "sadas",
                    f_name = name,
                    l_name = surName,
                    p_name = fatherland,
                    bin = if(type == TWO_STR) bin else iin,
                    birthday = birthday,
                    type = type,
                    certificate_number = if(type == TWO_STR) null else registerNumber,
                    email = email
                )
                setRegister(signUpClientRequest)
            }
        }
    }

    private fun setRegister(signUpSpecialistRequest: SignUpSpecialistRequest) {
        setLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.registration(signUpSpecialistRequest)
        }
    }

    private fun validateString(string: String, editText: EditText, errorText: String): Boolean {
        return if (string.isNotEmpty() && string.length >= TWO) {
            true
        } else {
            editText.error = errorText
            false
        }
    }

}