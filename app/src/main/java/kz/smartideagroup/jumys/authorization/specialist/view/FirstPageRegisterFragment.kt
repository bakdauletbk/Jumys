package kz.smartideagroup.jumys.authorization.specialist.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.elconfidencial.bubbleshowcase.BubbleShowCase
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder
import kotlinx.android.synthetic.main.first_page_register.*
import kotlinx.android.synthetic.main.first_page_register.btn_next
import kotlinx.android.synthetic.main.first_page_register.et_email
import kotlinx.android.synthetic.main.first_page_register.et_fatherland
import kotlinx.android.synthetic.main.first_page_register.et_iin
import kotlinx.android.synthetic.main.first_page_register.et_month
import kotlinx.android.synthetic.main.first_page_register.et_name
import kotlinx.android.synthetic.main.first_page_register.loadingViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.authorization.specialist.model.request.SignUpSpecialistRequest
import kz.smartideagroup.jumys.authorization.specialist.viewmodel.FirstPageViewModel
import kz.smartideagroup.jumys.authorization.specialist.viewmodel.PhoneNumberTransporter
import kz.smartideagroup.jumys.common.helpers.*
import kz.smartideagroup.jumys.common.utils.*
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class FirstPageRegisterFragment : BaseFragment(R.layout.first_page_register) {

    private lateinit var viewModel: FirstPageViewModel
    private lateinit var phoneNumberTransporter: PhoneNumberTransporter

    private var isValidateName = false
    private var isValidateSureName = false
    private var isValidateFatherLand = false
    private var isValidateDays = false
    private var isValidateMonth = false
    private var isValidateYears = false
    private var isValidateIin = false
    private var isValidateEmail = false
    private var type = ONE_STR

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
        phoneNumberTransporter.phone.observe(viewLifecycleOwner, {
            this.phone = it
        })

        viewModel.isError.observe(viewLifecycleOwner, {
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

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[FirstPageViewModel::class.java]
        phoneNumberTransporter =
            ViewModelProvider(requireActivity())[PhoneNumberTransporter::class.java]
    }

    private fun initListeners() {
        btn_next.onClick {
            prepareRegister()
        }

        getSimpleShowCaseBuilder().show()

        tv_text_view.onClick {
            getSimpleShowCaseBuilder().show()
        }
    }
    private fun getSimpleShowCaseBuilder(): BubbleShowCaseBuilder{
        return BubbleShowCaseBuilder(requireActivity())
            .title("Анкету можно скачать на сайте")
            .backgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
            .arrowPosition(BubbleShowCase.ArrowPosition.BOTTOM)
            .closeActionImage(null)
            .targetView(tv_text_view)
            .titleTextSize(14)
    }


    private fun prepareRegister() {
        val name = et_name.text.toString()
        val sureName = et_surname.text.toString()
        val fatherland = et_fatherland.text.toString()
        val days = et_days.text.toString()
        val month = et_month.text.toString()
        val years = et_years.text.toString()
        val email = et_email.text.toString()
        val iin = et_iin.text.toString()



        isValidateName = validateString(name, et_name, getString(R.string.enter_your_name))
        isValidateSureName =
            validateString(sureName, et_surname, getString(R.string.enter_your_sure_name))
        isValidateFatherLand =
            validateString(fatherland, et_fatherland, getString(R.string.enter_your_father))

        isValidateIin = et_iin.validateIin(iin, getString(R.string.enter_iin))
        isValidateDays = et_days.validateDays(days,getString(R.string.validate_day))
        isValidateMonth = et_month.validateMonth(month,getString(R.string.validate_month))
        isValidateYears = et_years.validateYear(years,getString(R.string.validate_year))
        isValidateEmail = et_email.validateEmail(email, getString(R.string.enter_your_email))
        when (isValidateIin && isValidateDays && isValidateFatherLand && isValidateMonth
                && isValidateName && isValidateSureName && isValidateYears) {
            true -> {
                val birthday = "$years-$month-$days"
                val signUpClientRequest = SignUpSpecialistRequest(
                    phone = phone,
                    ftoken = "sadas",
                    f_name = name,
                    l_name = sureName,
                    p_name = fatherland,
                    bin = iin,
                    birthday = birthday,
                    email = email,
                    type = type
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

    private fun setLoading(loading: Boolean) {
        loadingViews.visibility = if (loading) View.VISIBLE else View.GONE
    }

}