package kz.smartideagroup.jumys.authorization.manager.model.response

import com.google.gson.annotations.SerializedName

data class ProfileBody(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("m_user_id")
    val m_user_id: Long? = null,
    @SerializedName("full_name")
    val full_name: String? = null,
    @SerializedName("rating")
    val rating: String? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("created_at")
    val created_at: String? = null,
    @SerializedName("updated_at")
    val updated_at: String? = null,
)