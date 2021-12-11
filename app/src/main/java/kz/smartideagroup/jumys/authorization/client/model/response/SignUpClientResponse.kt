package kz.smartideagroup.jumys.authorization.client.model.response

import com.google.gson.annotations.SerializedName

data class SignUpClientResponse(
    @SerializedName("user")
    val user: UserBody
)