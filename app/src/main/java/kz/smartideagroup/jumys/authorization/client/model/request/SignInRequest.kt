package kz.smartideagroup.jumys.authorization.client.model.request

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("ftoken")
    val ftoken: String? = null,
    @SerializedName("password")
    val password: String? = null
)