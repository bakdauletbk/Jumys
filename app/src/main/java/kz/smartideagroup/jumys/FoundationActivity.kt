package kz.smartideagroup.jumys

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_foundation.*
import kz.smartideagroup.jumys.common.utils.ZERO
import kz.smartideagroup.jumys.common.views.BaseActivity
import org.jetbrains.anko.sdk27.coroutines.onClick

class FoundationActivity : BaseActivity() {

    /*
    isRole = true -> Client
    isRole = false -> Manager
     */

    companion object {
        const val ITEM_MENU = 2
    }

    private var isRole: Boolean? = null

    private var currentNavigationItemId = ZERO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foundation)
        lets()
    }

    private fun lets() {
        initNavController()
        initBottomNavigation()
    }

    @SuppressLint("ResourceType")
    private fun initBottomNavigation() {
        fab.onClick {
            when (isRole) {
                true -> navigateTo(R.id.mapClientFragment)
                false -> navigateTo(R.id.cameraFragment)
            }
        }
        /*
           isRole = true -> Client
           isRole = false -> Manager
         */
        bottom_navigation_bar.setOnNavigationItemSelectedListener { item ->
            if (item.itemId != currentNavigationItemId) {
                when (isRole) {
                    //isRole = true -> Client
                    true -> when (item.itemId) {
                        R.id.home_menu -> showHomeClientFragment()
                        R.id.claim_menu -> showClaimClientFragment()
                        R.id.profile_menu -> showProfileClientFragment()
                    }
                    //isRole = false -> Manager
                    false -> when (item.itemId) {
                        R.id.home_menu -> showHomeManagerFragment()
                        R.id.claim_menu -> showClaimManagerFragment()
                        R.id.profile_menu -> showProfileManagerFragment()
                    }
                }
            }
            when (isRole) {
                //isRole = true -> Client
                true -> when (item.itemId) {
                    R.id.support_menu -> showSupportClientFragment()
                }
                //isRole = false -> Manager
                false -> when (item.itemId) {
                    R.id.support_menu -> showSupportManagerFragment()
                }
            }

            true
        }
    }

    private fun showProfileManagerFragment() {
        currentNavigationItemId = R.id.profileManagerFragment
        navigateTo(R.id.profileManagerFragment)
    }

    private fun showSupportManagerFragment() {
        currentNavigationItemId = R.id.supportManagerFragment
        navigateTo(R.id.supportManagerFragment)
    }

    private fun showClaimManagerFragment() {
        currentNavigationItemId = R.id.claimManagerFragment
        navigateTo(R.id.claimManagerFragment)
    }

    private fun showHomeManagerFragment() {
        currentNavigationItemId = R.id.homeManagerFragment
        navigateTo(R.id.homeManagerFragment)
    }


    private fun showProfileClientFragment() {
        currentNavigationItemId = R.id.profileClientFragment
        navigateTo(R.id.profileClientFragment)
    }

    private fun showSupportClientFragment() {
        currentNavigationItemId = R.id.supportClientFragment
        navigateTo(R.id.supportClientFragment)
    }

    private fun showClaimClientFragment() {
        currentNavigationItemId = R.id.claimClientFragment
        navigateTo(R.id.claimClientFragment)
    }

    private fun showHomeClientFragment() {
        currentNavigationItemId = R.id.homeClientFragment
        navigateTo(R.id.homeClientFragment)
    }

    private fun destinationListeners(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                //Manager
                R.id.homeManagerFragment -> {
                    isRole = false
                    showBottomNavigation()
                }
                R.id.claimManagerFragment -> {
                    isRole = false
                    showBottomNavigation()
                }
                R.id.profileManagerFragment -> {
                    isRole = false
                    showBottomNavigation()
                }
                //Client
                R.id.homeClientFragment -> {
                    isRole = true
                    showBottomNavigation()
                }
                R.id.claimClientFragment -> {
                    isRole = true
                    showBottomNavigation()
                }
                R.id.profileClientFragment -> {
                    isRole = true
                    showBottomNavigation()
                }
                else -> hideBottomNavigation()
            }
        }
    }

    private fun showBottomNavigation() {
        fab.visibility = View.VISIBLE
        bottom_bar.visibility = View.VISIBLE
    }

    private fun initNavController() {
        val navController = findNavController(R.id.container)
        setupNavController(navController)
        destinationListeners(navController)
    }

    private fun setupNavController(navController: NavController) {
        bottom_navigation_bar.background = null
        bottom_navigation_bar.menu.getItem(ITEM_MENU).isEnabled = false
        bottom_navigation_bar.itemIconTintList = null
        bottom_navigation_bar.setupWithNavController(navController)
    }

    private fun hideBottomNavigation() {
        fab.visibility = View.GONE
        bottom_bar.visibility = View.GONE
    }

}