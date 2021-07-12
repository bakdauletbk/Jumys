package kz.smartideagroup.jumys.authorization.client.model

import android.app.Application
import kz.smartideagroup.jumys.BuildConfig
import kz.smartideagroup.jumys.authorization.client.model.request.SignUpClientRequest
import kz.smartideagroup.jumys.authorization.client.model.response.SignUpClientResponse
import kz.smartideagroup.jumys.authorization.client.model.response.UserBody
import kz.smartideagroup.jumys.common.views.BaseRepository
import retrofit2.Response

class RegistrationClientRepository(application: Application) : BaseRepository(application) {

    suspend fun registration(signUpClientRequest: SignUpClientRequest): Response<SignUpClientResponse> =
        networkService.signUpManager(
            BuildConfig.VERSION_NAME, signUpClientRequest
        )

    fun saveUser(userBody: UserBody){
        sessionManager.setIsAuthorize(true)
        sessionManager.setAccessToken(userBody.access_token.toString())
    }
}