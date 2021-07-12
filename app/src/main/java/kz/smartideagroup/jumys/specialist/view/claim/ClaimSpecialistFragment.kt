package kz.smartideagroup.jumys.specialist.view.claim

import android.os.Bundle
import androidx.activity.addCallback
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment

class ClaimSpecialistFragment : BaseFragment(R.layout.fragment_claim_specialist) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }
    }

}