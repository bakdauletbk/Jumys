package kz.smartideagroup.jumys.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.DELAY_THREE_SECOND
import kz.smartideagroup.jumys.common.views.BaseFragment

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private lateinit var viewModel: SplashViewModel
    private var isFirstLaunch = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initListeners()
        initObservers()
        initNetWorkChecker()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)
            .get(SplashViewModel::class.java)
    }

    private fun initListeners() {}

    private fun initNetWorkChecker() {
        CoroutineScope(Dispatchers.IO).launch {
            checkNetworkConnection()
        }
    }

    private fun initObservers() {
        viewModel.isNetworkConnected.observe(viewLifecycleOwner, {
            when (it) {
                true -> getIsAuthorized()
                false -> showErrorDialog(
                    getString(R.string.error_no_internet_title),
                    getString(R.string.error_no_internet_msg)
                )
            }
        })
        viewModel.isAuthorized.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                }
                false -> navigateTo(R.id.action_splashFragment_to_onBoardingFragment)
            }
        })
    }

    private fun getIsAuthorized() {
        viewModel.getIsAuthorized()
    }

    private suspend fun checkNetworkConnection() {
        when (isFirstLaunch) {
            true -> firstLaunch()
        }
    }

    private suspend fun firstLaunch() {
        isFirstLaunch = false
        delay(DELAY_THREE_SECOND)
        getIsNetworkConnected()
    }

    private fun getIsNetworkConnected() {
        viewModel.getIsNetworkConnected(requireContext())
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            checkNetworkConnection()
        }
    }
}