package kz.smartideagroup.jumys.authorization.specialist.model

import android.app.Application
import kz.smartideagroup.jumys.BuildConfig
import kz.smartideagroup.jumys.authorization.specialist.model.request.SignUpSpecialistRequest
import kz.smartideagroup.jumys.authorization.client.model.response.SignUpClientResponse
import kz.smartideagroup.jumys.authorization.client.model.response.UserBody
import kz.smartideagroup.jumys.common.views.BaseRepository
import retrofit2.Response

class FirstPageRepository(application: Application) : BaseRepository(application) {

    suspend fun registration(signUpSpecialistRequest: SignUpSpecialistRequest): Response<SignUpClientResponse> =
        networkService.signUpClient(
            BuildConfig.VERSION_NAME, signUpSpecialistRequest
        )

    fun saveUser(userBody: UserBody){
        sessionManager.setIsAuthorize(true)
        sessionManager.setAccessToken(userBody.access_token.toString())
    }

}