package kz.smartideagroup.jumys.authorization.manager.model

import android.app.Application
import kz.smartideagroup.jumys.BuildConfig
import kz.smartideagroup.jumys.authorization.manager.model.request.SignUpManagerRequest
import kz.smartideagroup.jumys.authorization.manager.model.response.SignUpManagerResponse
import kz.smartideagroup.jumys.authorization.manager.model.response.UserBody
import kz.smartideagroup.jumys.common.views.BaseRepository
import retrofit2.Response

class RegistrationManagerRepository(application: Application) : BaseRepository(application) {

    suspend fun registration(signUpManagerRequest: SignUpManagerRequest): Response<SignUpManagerResponse> =
        networkService.signUpManager(
            BuildConfig.VERSION_NAME, signUpManagerRequest
        )

    fun saveUser(userBody: UserBody){
        sessionManager.setIsAuthorize(true)
        sessionManager.setAccessToken(userBody.access_token.toString())
    }
}