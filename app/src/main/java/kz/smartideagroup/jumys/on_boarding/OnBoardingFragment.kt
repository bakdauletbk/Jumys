package kz.smartideagroup.jumys.on_boarding

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_on_boarding.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.ONE
import kz.smartideagroup.jumys.common.utils.ZERO
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class OnBoardingFragment : BaseFragment(R.layout.fragment_on_boarding) {

    private var isFirstOnBackPressed = true
    private var isNextScrollPage = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            when (isFirstOnBackPressed) {
                true -> applicationFinishNotify()
                false -> requireActivity().finish()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewPagerIndicators()
        initListeners()
    }

    private fun initViewPagerIndicators() {
        on_boarding_fragment_view_pager.adapter = OnBoardingPagerAdapter()
        on_boarding_fragment_view_pager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    ZERO -> isNextScrollPage = true
                    ONE -> isNextScrollPage = false
                }
            }

        })
    }

    private fun initListeners() {
        on_boarding_fragment_skip.onClick {
            when (isNextScrollPage) {
                true -> {
                    on_boarding_fragment_view_pager.setCurrentItem(ONE, true)
                    isNextScrollPage = false
                }
                false -> navigateTo(R.id.action_onBoardingFragment_to_roleFragment)
            }
        }
        iv_close.onClick {
            navigateTo(R.id.action_onBoardingFragment_to_roleFragment)
        }
    }

    private fun applicationFinishNotify() {
        showLongToast(getString(R.string.confirm_finish))
        isFirstOnBackPressed = false
    }

}