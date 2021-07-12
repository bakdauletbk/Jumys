package kz.smartideagroup.jumys.authorization.client.model.request

import com.google.gson.annotations.SerializedName

data class SignUpClientRequest(
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("ftoken")
    val ftoken: String? = null,
    @SerializedName("full_name")
    val full_name: String? = null,
    @SerializedName("email")
    val email: String? = null,
)