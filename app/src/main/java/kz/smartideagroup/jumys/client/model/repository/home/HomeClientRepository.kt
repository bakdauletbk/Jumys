package kz.smartideagroup.jumys.client.model.repository.home

import android.app.Application
import kz.smartideagroup.jumys.specialist.model.response.home.AdviceResponse
import kz.smartideagroup.jumys.client.model.response.home.BannerResponse
import kz.smartideagroup.jumys.client.model.response.home.ClaimResponse
import kz.smartideagroup.jumys.client.model.response.home.PopularQuestionResponse
import kz.smartideagroup.jumys.client.model.response.home.RecommendedSpecialistsResponse

class HomeClientRepository(application: Application) {

    fun getBanners(): ArrayList<BannerResponse> {
        val bannerResponse: ArrayList<BannerResponse> = ArrayList()
        bannerResponse.add(
            BannerResponse(
                img = "data:image/jpeg;base64,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"
            )
        )
        bannerResponse.add(
            BannerResponse(
                img = "data:image/jpeg;base64,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"
            )
        )
        bannerResponse.add(
            BannerResponse(
                img = "data:image/png;base64,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"
            )
        )
        bannerResponse.add(
            BannerResponse(
                img = "data:image/jpeg;base64,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"
            )
        )
        return bannerResponse
    }

    fun getPopularQuestion(): ArrayList<PopularQuestionResponse> {
        val popularQuestionResponse: ArrayList<PopularQuestionResponse> = ArrayList()
        popularQuestionResponse.add(
            PopularQuestionResponse(
                title = "Сколько стоит услуга?",
                description = "Специалисты являются самозанятыми лицами и каждый предлагает своя цену на услуги. Оставьте заказ с подробным описанием, изучите предложения и анкеты откликнувшихся специалистов и выберите для себя самый оптимальный вариант."
            )
        )
        popularQuestionResponse.add(
            PopularQuestionResponse(
                title = "Сколько стоит услуга?",
                description = "Специалисты являются самозанятыми лицами и каждый предлагает своя цену на услуги. Оставьте заказ с подробным описанием, изучите предложения и анкеты откликнувшихся специалистов и выберите для себя самый оптимальный вариант."
            )
        )
        return popularQuestionResponse
    }

    fun getSpecialists(): ArrayList<RecommendedSpecialistsResponse> {
        val recommendedSpecialistsResponse: ArrayList<RecommendedSpecialistsResponse> = ArrayList()

        recommendedSpecialistsResponse.add(
            RecommendedSpecialistsResponse(
                name = "Ермахан Өмір",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                rating = 4.9f,
                img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm88JZ6k0X4cf2T2Q8Z0zVFsIiQxQoevTV3A&usqp=CAU"
            )
        )
        recommendedSpecialistsResponse.add(
            RecommendedSpecialistsResponse(
                name = "Ермахан Өмір",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                rating = 4.9f,
                img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm88JZ6k0X4cf2T2Q8Z0zVFsIiQxQoevTV3A&usqp=CAU"
            )
        )
        recommendedSpecialistsResponse.add(
            RecommendedSpecialistsResponse(
                name = "Ермахан Өмір",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                rating = 4.9f,
                img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm88JZ6k0X4cf2T2Q8Z0zVFsIiQxQoevTV3A&usqp=CAU"
            )
        )
        recommendedSpecialistsResponse.add(
            RecommendedSpecialistsResponse(
                name = "Ермахан Өмір",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                rating = 4.9f,
                img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm88JZ6k0X4cf2T2Q8Z0zVFsIiQxQoevTV3A&usqp=CAU"
            )
        )
        recommendedSpecialistsResponse.add(
            RecommendedSpecialistsResponse(
                name = "Ермахан Өмір",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                rating = 4.9f,
                img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm88JZ6k0X4cf2T2Q8Z0zVFsIiQxQoevTV3A&usqp=CAU"
            )
        )
        recommendedSpecialistsResponse.add(
            RecommendedSpecialistsResponse(
                name = "Ермахан Өмір",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                rating = 4.9f,
                img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm88JZ6k0X4cf2T2Q8Z0zVFsIiQxQoevTV3A&usqp=CAU"
            )
        )

        return recommendedSpecialistsResponse
    }

    fun getClaim(): ArrayList<ClaimResponse> {
        val claimResponse: ArrayList<ClaimResponse> = ArrayList()

        claimResponse.add(
            ClaimResponse(
                title = "Заявка от Иванова И.",
                description = "Уборка, химчистка и т.д",
                detail = "Помощь по хозяйству",
                price = 2000.0
            )
        )
        claimResponse.add(
            ClaimResponse(
                title = "Заявка от Иванова И.",
                description = "Уборка, химчистка и т.д",
                detail = "Помощь по хозяйству",
                price = 2000.0
            )
        )
        claimResponse.add(
            ClaimResponse(
                title = "Заявка от Иванова И.",
                description = "Уборка, химчистка и т.д",
                detail = "Помощь по хозяйству",
                price = 2000.0
            )
        )
        claimResponse.add(
            ClaimResponse(
                title = "Заявка от Иванова И.",
                description = "Уборка, химчистка и т.д",
                detail = "Помощь по хозяйству",
                price = 2000.0
            )
        )

        return claimResponse
    }

    fun getAdvice(): ArrayList<AdviceResponse> {
        val adviceResponse: ArrayList<AdviceResponse> = ArrayList()

        adviceResponse.add(
            AdviceResponse(
                title = "Как получить заказ?",
                description = "Во вкладке “Новые заказы” выбрав интересующий заказ вы можете откликнутся либо же позвонить клиенту"
            )
        )
        adviceResponse.add(
            AdviceResponse(
                title = "Как получить заказ?",
                description = "Во вкладке “Новые заказы” выбрав интересующий заказ вы можете откликнутся либо же позвонить клиенту"
            )
        )
        adviceResponse.add(
            AdviceResponse(
                title = "Как получить заказ?",
                description = "Во вкладке “Новые заказы” выбрав интересующий заказ вы можете откликнутся либо же позвонить клиенту"
            )
        )

        return adviceResponse
    }


}