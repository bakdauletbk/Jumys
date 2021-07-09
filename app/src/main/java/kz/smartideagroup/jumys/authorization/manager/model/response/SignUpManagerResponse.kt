package kz.smartideagroup.jumys.authorization.manager.model.response

import com.google.gson.annotations.SerializedName

data class SignUpManagerResponse(
    @SerializedName("user")
    val user: UserBody,
)