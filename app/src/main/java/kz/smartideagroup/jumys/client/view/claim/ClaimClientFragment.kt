package kz.smartideagroup.jumys.client.view.claim

import android.os.Bundle
import androidx.activity.addCallback
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment

class ClaimClientFragment : BaseFragment(R.layout.fragment_claim_client) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }
    }

}