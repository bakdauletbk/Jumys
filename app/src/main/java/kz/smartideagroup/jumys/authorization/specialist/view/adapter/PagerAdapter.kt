package kz.smartideagroup.jumys.authorization.specialist.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kz.smartideagroup.jumys.authorization.specialist.view.FirstPageRegisterFragment
import kz.smartideagroup.jumys.authorization.specialist.view.SecondPageRegisterFragment

class PagerAdapter(fm: FragmentManager, private var totalTabs: Int) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FirstPageRegisterFragment()
            1 -> SecondPageRegisterFragment()
            else -> getItem(position)
        }
    }
}