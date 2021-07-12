package kz.smartideagroup.jumys.authorization.client.model.request

import com.google.gson.annotations.SerializedName

data class AuthSmsRequest(
    @SerializedName("phone")
    val phone: String? = null,
)