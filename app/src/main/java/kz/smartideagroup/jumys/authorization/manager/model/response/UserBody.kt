package kz.smartideagroup.jumys.authorization.manager.model.response

import com.google.gson.annotations.SerializedName

data class UserBody (
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("access_token")
    val access_token: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("profile")
    val profile: ProfileBody? = null,
)