package kz.smartideagroup.jumys.authorization.specialist.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.first_page_register.*
import kotlinx.android.synthetic.main.first_page_register.btn_next
import kotlinx.android.synthetic.main.first_page_register.et_name
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.authorization.specialist.model.request.SignUpSpecialistRequest
import kz.smartideagroup.jumys.authorization.specialist.viewmodel.FirstPageViewModel
import kz.smartideagroup.jumys.common.helpers.validateDays
import kz.smartideagroup.jumys.common.helpers.validateIin
import kz.smartideagroup.jumys.common.helpers.validateYear
import kz.smartideagroup.jumys.common.utils.PUT_PHONE
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class FirstPageRegisterFragment : BaseFragment(R.layout.first_page_register) {

    private lateinit var viewModel: FirstPageViewModel

    private var isValidateName = false
    private var isValidateSureName = false
    private var isValidateFatherLand = false
    private var isValidateDays = false
    private var isValidateMonth = false
    private var isValidateYears = false
    private var isValidateIin = false

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
            errorDialog(getString(R.string.error_no_internet_msg))
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, {
            when (it) {
                true -> navigateTo(R.id.homeSpecialistFragment)
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
    }

    private fun initListeners() {
        btn_next.onClick {
            prepareRegister()
        }
    }

    private fun prepareRegister() {
        val phone = arguments?.getString(PUT_PHONE) as String
        val name = et_name.text.toString()
        val sureName = et_surname.text.toString()
        val fatherland = et_fatherland.text.toString()
        val days = et_days.text.toString()
        val month = et_month.text.toString()
        val years = et_years.text.toString()
        val iin = et_iin.text.toString()

        isValidateName = validateString(name, et_name, getString(R.string.enter_your_name))
        isValidateSureName =
            validateString(sureName, et_surname, getString(R.string.enter_your_sure_name))
        isValidateFatherLand =
            validateString(fatherland, et_fatherland, getString(R.string.enter_your_father))

        isValidateIin = et_iin.validateIin(iin, getString(R.string.enter_iin))
        isValidateDays = et_days.validateDays(days)
        isValidateMonth = et_month.validateDays(month)
        isValidateYears = et_years.validateYear(years)

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
                    birthday = birthday
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
        return if (string.isNotEmpty()) {
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