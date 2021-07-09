package kz.smartideagroup.jumys.authorization.manager.model.request

import com.google.gson.annotations.SerializedName

data class VerificationRequest(
    @SerializedName("activationcode")
    val activationcode: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("role")
    val role: String? = null,

)