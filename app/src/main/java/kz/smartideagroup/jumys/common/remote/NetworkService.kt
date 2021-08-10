package kz.smartideagroup.jumys.common.remote

import kz.smartideagroup.jumys.authorization.client.model.request.AuthSmsRequest
import kz.smartideagroup.jumys.authorization.client.model.request.SignInRequest
import kz.smartideagroup.jumys.authorization.client.model.request.SignUpClientRequest
import kz.smartideagroup.jumys.authorization.client.model.request.VerificationRequest
import kz.smartideagroup.jumys.authorization.client.model.response.SignUpClientResponse
import kz.smartideagroup.jumys.authorization.client.model.response.VerificationResponse
import kz.smartideagroup.jumys.authorization.specialist.model.request.SignUpSpecialistRequest
import kz.smartideagroup.jumys.client.model.request.apply_claim.RequestWorkUpload
import kz.smartideagroup.jumys.specialist.model.response.apply_claim.AddressOrderResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

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
        @Body signUpClientRequest: SignUpClientRequest,
    ): Response<SignUpClientResponse>

    @POST(EndPoints.POST_SIGN_UP_CLIENT)
    suspend fun signUpClient(
        @Header("appVer") appVer: String,
        @Body signUpClientRequest: SignUpSpecialistRequest,
    ): Response<SignUpClientResponse>
    @GET(EndPoints.GET_WORK)
    suspend fun workList(
        @Query("appVer") appVer: String
    ) : AddressOrderResponse

}