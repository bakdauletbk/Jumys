package kz.smartideagroup.jumys.splash

import android.app.Application
import android.content.Context
import kz.smartideagroup.jumys.common.helpers.NetworkHelpers
import kz.smartideagroup.jumys.common.views.BaseRepository

class SplashRepository(application: Application) : BaseRepository(application) {

    fun getIsNetworkConnected(context: Context): Boolean {
        return NetworkHelpers.isNetworkConnected(context)
    }

    fun getIsAuthorized(): Boolean {
        return sessionManager.getIsAuthorize()
    }

}