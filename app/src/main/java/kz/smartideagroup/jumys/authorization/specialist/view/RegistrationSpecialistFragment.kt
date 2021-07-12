package kz.smartideagroup.jumys.authorization.specialist.view

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_registration_specialist.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.authorization.specialist.view.adapter.PagerAdapter
import kz.smartideagroup.jumys.common.utils.*
import kz.smartideagroup.jumys.common.views.BaseFragment

class RegistrationSpecialistFragment : BaseFragment(R.layout.fragment_registration_specialist) {

    companion object {
        const val PRIVATE_PERSON_TAB = "Частное лицо"
        const val IP_TOO_TAB = "ИП/ТОО"
        const val ONE_FLOAT = 1f
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewPager()
    }

    private fun initViewPager() {
        tab_layout.addTab(tab_layout.newTab().setText(PRIVATE_PERSON_TAB))
        tab_layout.addTab(tab_layout.newTab().setText(IP_TOO_TAB))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL
        tab_layout.setTabWidthAsWrapContents(ZERO)
        tab_layout.setTabWidthAsWrapContents(ONE)

        val adapter = PagerAdapter(childFragmentManager, tab_layout.tabCount)
        vp_questionnaire!!.adapter = adapter
        vp_questionnaire.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vp_questionnaire.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun TabLayout.setTabWidthAsWrapContents(tabPosition: Int) {
        val layout =
            (this.getChildAt(ZERO) as LinearLayout).getChildAt(tabPosition) as LinearLayout
        val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = ONE_FLOAT
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        layout.layoutParams = layoutParams
    }

}