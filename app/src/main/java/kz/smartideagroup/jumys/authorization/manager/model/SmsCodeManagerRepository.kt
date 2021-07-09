package kz.smartideagroup.jumys.authorization.manager.model

import android.app.Application
import kz.smartideagroup.jumys.BuildConfig
import kz.smartideagroup.jumys.authorization.manager.model.request.VerificationRequest
import kz.smartideagroup.jumys.authorization.manager.model.response.UserResponse
import kz.smartideagroup.jumys.authorization.manager.model.response.VerificationResponse
import kz.smartideagroup.jumys.common.views.BaseRepository
import retrofit2.Response

class SmsCodeManagerRepository(application: Application) : BaseRepository(application) {

    suspend fun verification(verificationRequest: VerificationRequest): Response<VerificationResponse> =
        networkService.verification(
            BuildConfig.VERSION_NAME, verificationRequest
        )

    fun saveUserInfo(userResponse: UserResponse) {
        sessionManager.setIsAuthorize(true)
        sessionManager.setAccessToken(userResponse.access_token.toString())
    }
}