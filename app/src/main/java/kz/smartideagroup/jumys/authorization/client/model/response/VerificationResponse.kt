package kz.smartideagroup.jumys.authorization.client.model.response

import com.google.gson.annotations.SerializedName

data class VerificationResponse(
    @SerializedName("user")
    val user: UserResponse,
)