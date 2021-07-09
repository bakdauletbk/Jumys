package kz.smartideagroup.jumys.manager.model.request.apply_claim

import com.google.gson.annotations.SerializedName

data class RequestWorkUpload(
    @SerializedName("work")
    val work: Int? = null,
    @SerializedName("video")
    val video: String? = null
)