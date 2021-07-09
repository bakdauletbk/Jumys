package kz.smartideagroup.jumys.authorization.client.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.first_page_register.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.authorization.client.viewmodel.FirstPageViewModel
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class FirstPageRegisterFragment : BaseFragment(R.layout.first_page_register) {

    private lateinit var viewModel: FirstPageViewModel

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
                true -> {
                }
                false -> {
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
            navigateTo(R.id.homeClientFragment)
        }
    }

    private fun setLoading(loading: Boolean) {

    }

}