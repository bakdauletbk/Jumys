package kz.smartideagroup.jumys.authorization.client.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.second_page_register.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.authorization.client.viewmodel.SecondPageViewModel
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class SecondPageRegisterFragment : BaseFragment(R.layout.second_page_register) {

    private lateinit var viewModel: SecondPageViewModel

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

    private fun setLoading(loading: Boolean) {

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[SecondPageViewModel::class.java]
    }

    private fun initListeners() {
        btn_next.onClick {
            prepareRegister()
        }


        too.setOnClickListener {
            enter_bin.visibility = View.VISIBLE
            image_view2.visibility = View.VISIBLE
            line15.visibility = View.VISIBLE
            image_view.visibility = View.GONE
            line14.visibility = View.GONE
            register_number.visibility = View.GONE
            line12.visibility = View.GONE
        }

        ip.setOnClickListener {
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

    private fun prepareRegister() {

    }

}