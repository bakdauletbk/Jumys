package kz.smartideagroup.jumys.manager.view.home

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_manager.*
import kotlinx.coroutines.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.client.model.response.home.AdviceResponse
import kz.smartideagroup.jumys.common.utils.*
import kz.smartideagroup.jumys.common.views.BaseFragment
import kz.smartideagroup.jumys.manager.model.response.home.*
import kz.smartideagroup.jumys.manager.view.home.adapter.BannerAdapter
import kz.smartideagroup.jumys.manager.view.home.adapter.ClaimAdapter
import kz.smartideagroup.jumys.manager.view.home.adapter.PopularQuestionsAdapter
import kz.smartideagroup.jumys.manager.view.home.adapter.RecommendedSpecialistsAdapter
import kz.smartideagroup.jumys.manager.viewmodel.home.HomeManagerViewModel
import java.util.ArrayList

class HomeManagerFragment : BaseFragment(R.layout.fragment_home_manager) {

    private lateinit var viewModel: HomeManagerViewModel

    private val bannersAdapter by lazy { BannerAdapter(context) }

    private val adviceAdapter: AdviceManagerAdapter =
        AdviceManagerAdapter(this)

    private val claimAdapter: ClaimAdapter =
        ClaimAdapter(this)

    private val recommendedAdapter: RecommendedSpecialistsAdapter =
        RecommendedSpecialistsAdapter(this)

    private val popularQuestionsAdapter: PopularQuestionsAdapter =
        PopularQuestionsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initRecyclerView()
        updateFeeds()
        initListeners()
        initObservers()
        setBannerContent()
    }

    private fun initRecyclerView() {
        view_pager.apply {
            adapter = bannersAdapter
            setPadding(
                convertDpToPixel(PADDING),
                PADDING_TOP,
                convertDpToPixel(PADDING),
                PADDING_BOTTOM
            )
            pageMargin = convertDpToPixel(PAGE_MARGIN)
            clipToPadding = false
        }
        rv_advice_manager.apply {
            adapter = adviceAdapter
            layoutManager = LinearLayoutManager(context)
        }
        rv_claims_manager.apply {
            adapter = claimAdapter
            layoutManager = LinearLayoutManager(context)
        }
        rv_recommended_specialists.apply {
            adapter = recommendedAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        rv_popular_questions.apply {
            adapter = popularQuestionsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateFeeds() {
        viewModel.getAdvice()
        viewModel.getClaim()
        viewModel.getSpecialists()
        viewModel.getPopularQuestion()
        viewModel.getBanners()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[HomeManagerViewModel::class.java]
    }

    private fun initListeners() {

    }

    private fun initObservers() {
        viewModel.claimResponse.observe(viewLifecycleOwner, {
            setClaimList(it)
        })
        viewModel.adviceResponse.observe(viewLifecycleOwner, {
            setAdviceList(it)
        })
        viewModel.recommendedSpecialists.observe(viewLifecycleOwner, {
            setRecommendedList(it)
        })
        viewModel.popularQuestion.observe(viewLifecycleOwner, {
            setPopularQuestion(it)
        })
        viewModel.bannerResponse.observe(viewLifecycleOwner, {
            addSliderList(it)
        })
    }

    private fun addSliderList(sliderList: List<BannerResponse>) {
        bannersAdapter.addImageSlider(sliderList)
    }

    private fun setBannerContent() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (view_pager.currentItem != bannersAdapter.count.minus(ONE)) {
                    view_pager.setCurrentItem(
                        view_pager.currentItem.plus(ONE),
                        true
                    )
                } else {
                    view_pager.setCurrentItem(ZERO, true)
                }
                delay(TIME_MILLIS)

                setBannerContent()
                this.cancel()
            } catch (e: Exception) {
            }
        }
    }

    private fun setPopularQuestion(it: List<PopularQuestionResponse>?) {
        popularQuestionsAdapter.addList(it!!)
    }

    private fun setRecommendedList(it: List<RecommendedSpecialistsResponse>?) {
        recommendedAdapter.addList(it!!)
    }

    private fun setAdviceList(it: List<AdviceResponse>?) {
        adviceAdapter.addList(it!!)
    }

    private fun setClaimList(it: List<ClaimResponse>?) {
        claimAdapter.addList(it!!)
    }

}