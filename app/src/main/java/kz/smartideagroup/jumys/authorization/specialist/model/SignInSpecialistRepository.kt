package kz.smartideagroup.jumys.authorization.specialist.model

import android.app.Application
import kz.smartideagroup.jumys.BuildConfig
import kz.smartideagroup.jumys.authorization.client.model.request.AuthSmsRequest
import kz.smartideagroup.jumys.common.views.BaseRepository
import okhttp3.ResponseBody
import retrofit2.Response

class SignInSpecialistRepository(application: Application) : BaseRepository(application) {

    suspend fun sendSms(authSmsRequest: AuthSmsRequest): Response<ResponseBody> =
        networkService.authSms(
            BuildConfig.VERSION_NAME, authSmsRequest
        )

}