package kz.smartideagroup.jumys.authorization.client.model.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("access_token")
    val access_token: String? = null,
    @SerializedName("email")
    val email: String? = null,
)