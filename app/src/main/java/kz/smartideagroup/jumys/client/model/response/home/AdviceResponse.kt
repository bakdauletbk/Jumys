package kz.smartideagroup.jumys.client.model.response.home

import android.icu.text.CaseMap
import com.google.gson.annotations.SerializedName

data class AdviceResponse(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null
)