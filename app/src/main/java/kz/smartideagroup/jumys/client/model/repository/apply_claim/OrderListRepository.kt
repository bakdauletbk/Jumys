package kz.smartideagroup.jumys.client.model.repository.apply_claim

import android.app.Application
import kz.smartideagroup.jumys.client.model.response.apply_claim.AddressOrderResponse

class OrderListRepository(application: Application) {

    fun getOrderList(): List<AddressOrderResponse> {
        val addressOrderResponse: ArrayList<AddressOrderResponse> = ArrayList()
        addressOrderResponse.add(
            AddressOrderResponse(
                name = "Ермахан Ө.",
                price = 5000.0,
                address = "Рыскулова 84",
                number = "77771468011",
                latitude = 42.35179038306585,
                longitude = 69.58333475710809,
                img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                img2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                img3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                description = "Уборка квартиры, 2 дня работы."
            )
        )
        addressOrderResponse.add(
            AddressOrderResponse(
                name = "Ермахан Ө.",
                price = 10500.5,
                address = "Рыскулова 84",
                number = "77771468011",
                latitude = 42.33834729839286, longitude = 69.64207080016527,
                img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                img2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                img3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                description = "Уборка квартиры, 2 дня работы."
            )
        )
        addressOrderResponse.add(
            AddressOrderResponse(
                name = "Ермахан Ө.",
                price = 8000.0,
                address = "Рыскулова 84",
                number = "77771468011",
                latitude = 42.32350117518738, longitude = 69.6095642014665,
                img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                img2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                img3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                description = "Уборка квартиры, 2 дня работы."
            )
        )
        addressOrderResponse.add(
            AddressOrderResponse(
                name = "Ермахан Ө.",
                price = 7500.5,
                address = "Рыскулова 84",
                number = "77771468011",
                latitude = 42.32696750969979, longitude = 69.58096464644576,
                img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                img2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                img3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU",
                description = "Уборка квартиры, 2 дня работы."
            )
        )

        return addressOrderResponse
    }

}