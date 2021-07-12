package kz.smartideagroup.jumys.specialist.model.repository.home

import android.app.Application
import android.util.Log
import kz.smartideagroup.jumys.specialist.model.response.home.AdviceResponse
import kz.smartideagroup.jumys.specialist.model.response.home.CategoryResponse
import kz.smartideagroup.jumys.specialist.model.response.home.HistoryResponse

class HomeSpecialistRepository(application: Application) {

    companion object {
        const val TAG = "HomeClientRepository"
    }

    fun getHistory(): ArrayList<HistoryResponse> {
        val historyResponse: ArrayList<HistoryResponse> = ArrayList()
        historyResponse.add(HistoryResponse(img ="https://lizasenglish.ru/wp-content/uploads/2016/09/Tests.png"))
        historyResponse.add(HistoryResponse(img = "data:image/jpeg;base64,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"))
        historyResponse.add(HistoryResponse(img = "data:image/jpeg;base64,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"))
        historyResponse.add(HistoryResponse(img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU"))
        historyResponse.add(HistoryResponse(img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU"))
        historyResponse.add(HistoryResponse(img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU"))
        historyResponse.add(HistoryResponse(img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcq94044Nt1psQELel4cskiq827HL2CAMoCw&usqp=CAU"))
        return historyResponse
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

    fun getCategory(): ArrayList<CategoryResponse>? {
        try {
            val categoryList: ArrayList<CategoryResponse> = ArrayList()

            categoryList.add(
                CategoryResponse(
                    img = "https://lizasenglish.ru/wp-content/uploads/2016/09/Tests.png",
                    title = "Помощь по хозяйству",
                    description = "Уборка, химчистка, садовники и т.д"
                )
            )
            categoryList.add(
                CategoryResponse(
                    img = "https://lizasenglish.ru/wp-content/uploads/2016/09/Tests.png",
                    title = "Помощь по хозяйству",
                    description = "Уборка, химчистка, садовники и т.д"
                )
            )
            categoryList.add(
                CategoryResponse(
                    img = "https://lizasenglish.ru/wp-content/uploads/2016/09/Tests.png",
                    title = "Помощь по хозяйству",
                    description = "Уборка, химчистка, садовники и т.д"
                )
            )
            categoryList.add(
                CategoryResponse(
                    img = "https://lizasenglish.ru/wp-content/uploads/2016/09/Tests.png",
                    title = "Помощь по хозяйству",
                    description = "Уборка, химчистка, садовники и т.д"
                )
            )

            return categoryList
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            return null
        }

    }

}