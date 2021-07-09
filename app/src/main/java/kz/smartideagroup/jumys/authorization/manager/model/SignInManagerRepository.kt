package kz.smartideagroup.jumys.authorization.manager.model

import android.app.Application
import kz.smartideagroup.jumys.BuildConfig
import kz.smartideagroup.jumys.authorization.manager.model.request.AuthSmsRequest
import kz.smartideagroup.jumys.common.views.BaseRepository
import okhttp3.ResponseBody
import retrofit2.Response

class SignInManagerRepository(application: Application) : BaseRepository(application) {

    suspend fun sendSms(authSmsRequest: AuthSmsRequest): Response<ResponseBody> =
        networkService.authSms(
            BuildConfig.VERSION_NAME, authSmsRequest
        )

}