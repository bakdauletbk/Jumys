package kz.smartideagroup.jumys.manager.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kz.smartideagroup.jumys.client.model.response.home.AdviceResponse
import kz.smartideagroup.jumys.manager.model.repository.home.HomeManagerRepository
import kz.smartideagroup.jumys.manager.model.response.home.BannerResponse
import kz.smartideagroup.jumys.manager.model.response.home.ClaimResponse
import kz.smartideagroup.jumys.manager.model.response.home.PopularQuestionResponse
import kz.smartideagroup.jumys.manager.model.response.home.RecommendedSpecialistsResponse

class HomeManagerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HomeManagerRepository(application)

    val adviceResponse = MutableLiveData<List<AdviceResponse>>()
    val claimResponse = MutableLiveData<List<ClaimResponse>>()
    val recommendedSpecialists = MutableLiveData<List<RecommendedSpecialistsResponse>>()
    val popularQuestion = MutableLiveData<List<PopularQuestionResponse>>()
    val bannerResponse = MutableLiveData<List<BannerResponse>>()

    fun getBanners() {
        bannerResponse.postValue(repository.getBanners())
    }

    fun getAdvice() {
        adviceResponse.postValue(repository.getAdvice())
    }

    fun getClaim() {
        claimResponse.postValue(repository.getClaim())
    }

    fun getSpecialists() {
        recommendedSpecialists.postValue(repository.getSpecialists())
    }

    fun getPopularQuestion() {
        popularQuestion.postValue(repository.getPopularQuestion())
    }

}