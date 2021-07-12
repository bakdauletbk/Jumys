package kz.smartideagroup.jumys.specialist.model.response.apply_claim

import java.io.Serializable

data class AddressOrderResponse(
    val price: Double? = null,
    val img: String? = null,
    val img2: String? = null,
    val img3: String? = null,
    val address: String? = null,
    val name: String? = null,
    val number: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val description: String? = null
):Serializable