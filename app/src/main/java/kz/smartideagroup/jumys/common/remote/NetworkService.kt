package kz.smartideagroup.jumys.common.remote

import kz.smartideagroup.jumys.authorization.client.model.SignUpClientRequest
import kz.smartideagroup.jumys.authorization.manager.model.request.AuthSmsRequest
import kz.smartideagroup.jumys.authorization.manager.model.request.SignInRequest
import kz.smartideagroup.jumys.authorization.manager.model.request.SignUpManagerRequest
import kz.smartideagroup.jumys.authorization.manager.model.request.VerificationRequest
import kz.smartideagroup.jumys.authorization.manager.model.response.SignUpManagerResponse
import kz.smartideagroup.jumys.authorization.manager.model.response.VerificationResponse
import kz.smartideagroup.jumys.manager.model.request.apply_claim.RequestWorkUpload
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService {

    @POST(EndPoints.POST_WORK_UPLOAD)
    suspend fun setWorkUpload(
        @Header("appVer") appVer: String,
        @Header("Authorization") Authorization: String,
        @Body requestWorkUpload: RequestWorkUpload,
    ): Response<ResponseBody>

    @POST(EndPoints.POST_SIGN_IN)
    suspend fun signIn(
        @Header("appVer") appVer: String,
        @Header("Authorization") Authorization: String,
        @Body signInRequest: SignInRequest,
    )

    @POST(EndPoints.POST_AUTH_SMS)
    suspend fun authSms(
        @Header("appVer") appVer: String,
        @Body authSmsRequest: AuthSmsRequest,
    ): Response<ResponseBody>

    @POST(EndPoints.POST_AUTH_VERIFICATION)
    suspend fun verification(
        @Header("appVer") appVer: String,
        @Body verificationRequest: VerificationRequest,
    ): Response<VerificationResponse>

    @POST(EndPoints.POST_SIGN_UP_MANAGER)
    suspend fun signUpManager(
        @Header("appVer") appVer: String,
        @Body signUpManagerRequest: SignUpManagerRequest,
    ): Response<SignUpManagerResponse>

    @POST(EndPoints.POST_SIGN_UP_CLIENT)
    suspend fun signUpClient(
        @Header("appVer") appVer: String,
        @Body signUpClientRequest: SignUpClientRequest,
    )

}