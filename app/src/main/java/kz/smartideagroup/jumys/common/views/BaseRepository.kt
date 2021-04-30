package kz.smartideagroup.jumys.common.views

import android.app.Application
import android.content.Context
import kz.smartideagroup.jumys.common.preference.SessionManager
import kz.smartideagroup.jumys.common.remote.ApiConstants
import kz.smartideagroup.jumys.common.remote.Networking
import kz.smartideagroup.jumys.common.utils.CONTENT_TYPE_JSON
import org.jetbrains.anko.appcompatV7.BuildConfig

open class BaseRepository(application: Application, baseUrl: String = ApiConstants.JAVA_BASE_URL) {

    val applicationVersion = BuildConfig.VERSION_NAME
    val contentType = CONTENT_TYPE_JSON

    val networkService =
        Networking.create(baseUrl)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    var sessionManager: SessionManager =
        SessionManager(sharedPreferences)

}