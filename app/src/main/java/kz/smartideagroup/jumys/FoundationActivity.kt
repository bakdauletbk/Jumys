package kz.smartideagroup.jumys

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_foundation.*
import kz.smartideagroup.jumys.common.utils.ZERO
import kz.smartideagroup.jumys.common.views.BaseActivity
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.nio.ByteBuffer

class FoundationActivity : BaseActivity() {

    /*
    isRole = true -> Client
    isRole = false -> Manager
     */


    companion object {
        const val ITEM_MENU = 2
        const val SUPPORT_MENU = 3
        const val HOME_MENU = 0
        const val CLAIM_MENU = 1
        const val PROFILE_MENU = 4
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
        currentNavigationItemId = R.id.profile_menu
        navigateTo(R.id.profileManagerFragment)
    }

    private fun showSupportManagerFragment() {
        navigateTo(R.id.supportManagerFragment)
    }

    private fun showClaimManagerFragment() {
        currentNavigationItemId = R.id.claim_menu
        navigateTo(R.id.claimManagerFragment)
    }

    private fun showHomeManagerFragment() {
        currentNavigationItemId = R.id.home_menu
        navigateTo(R.id.homeManagerFragment)
    }

    private fun showProfileClientFragment() {
        currentNavigationItemId = R.id.profile_menu
        navigateTo(R.id.profileClientFragment)
    }

    private fun showSupportClientFragment() {
        navigateTo(R.id.supportClientFragment)
    }

    private fun showClaimClientFragment() {
        currentNavigationItemId = R.id.claim_menu
        navigateTo(R.id.claimClientFragment)
    }

    private fun showHomeClientFragment() {
        currentNavigationItemId = R.id.home_menu
        navigateTo(R.id.homeClientFragment)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun destinationListeners(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                //Manager
                R.id.homeManagerFragment -> {
                    isRole = false
                    currentNavigationItemId = R.id.home_menu
                    bottom_navigation_bar.menu.getItem(HOME_MENU).isChecked = true
                    showBottomNavigation()
                }
                R.id.claimManagerFragment -> {
                    isRole = false
                    bottom_navigation_bar.menu.getItem(CLAIM_MENU).isChecked = true
                    showBottomNavigation()
                }
                R.id.profileManagerFragment -> {
                    isRole = false
                    bottom_navigation_bar.menu.getItem(PROFILE_MENU).isChecked = true
                    showBottomNavigation()
                }
                //Client
                R.id.homeClientFragment -> {
                    isRole = true
                    currentNavigationItemId = R.id.home_menu
                    bottom_navigation_bar.menu.getItem(HOME_MENU).isChecked = true
                    showBottomNavigation()
                }
                R.id.claimClientFragment -> {
                    isRole = true
                    bottom_navigation_bar.menu.getItem(CLAIM_MENU).isChecked = true
                    showBottomNavigation()
                }
                R.id.profileClientFragment -> {
                    isRole = true
                    bottom_navigation_bar.menu.getItem(PROFILE_MENU).isChecked = true
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
        bottom_navigation_bar.menu.getItem(SUPPORT_MENU).isCheckable = false
        bottom_navigation_bar.menu.getItem(ITEM_MENU).isEnabled = false
        bottom_navigation_bar.itemIconTintList = null
        bottom_navigation_bar.setupWithNavController(navController)
    }

    private fun hideBottomNavigation() {
        bottom_bar.visibility = View.GONE
        fab.visibility = View.GONE
    }

}