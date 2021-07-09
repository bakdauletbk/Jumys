package kz.smartideagroup.jumys.authorization.client.model.request

import com.google.gson.annotations.SerializedName

data class SignUpClientRequest(
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("ftoken")
    val ftoken: String? = null,
    @SerializedName("f_name")
    val f_name: String? = null,
    @SerializedName("l_name")
    val l_name: String? = null,
    @SerializedName("p_name")
    val p_name: String? = null,
    @SerializedName("birthday")
    val birthday: String? = null,
    @SerializedName("bin")
    val bin: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("certificate_number")
    val certificate_number: Long? = null,
    @SerializedName("certificate_photo")
    val certificate_photo: String? = null,
    @SerializedName("email")
    val email: String? = null,
)