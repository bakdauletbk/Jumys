package kz.smartideagroup.jumys.specialist.model.response.home

import com.google.gson.annotations.SerializedName

data class AdviceResponse(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null
)