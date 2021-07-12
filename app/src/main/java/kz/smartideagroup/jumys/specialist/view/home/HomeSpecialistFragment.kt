package kz.smartideagroup.jumys.specialist.view.home

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_specialist.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.specialist.model.response.home.AdviceResponse
import kz.smartideagroup.jumys.specialist.model.response.home.CategoryResponse
import kz.smartideagroup.jumys.specialist.model.response.home.HistoryResponse
import kz.smartideagroup.jumys.specialist.view.home.adapter.AdviceAdapter
import kz.smartideagroup.jumys.specialist.view.home.adapter.CategoryAdapter
import kz.smartideagroup.jumys.specialist.view.home.adapter.HistoryAdapter
import kz.smartideagroup.jumys.specialist.viewmodel.home.HomeSpecialistViewModel
import kz.smartideagroup.jumys.common.utils.TWO
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class HomeSpecialistFragment : BaseFragment(R.layout.fragment_home_specialist) {

    private lateinit var viewModel: HomeSpecialistViewModel

    private val categoryAdapter: CategoryAdapter =
        CategoryAdapter(this)

    private val historyAdapter: HistoryAdapter =
        HistoryAdapter(this)

    private val adviceAdapter: AdviceAdapter =
        AdviceAdapter(this)

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
    }

    private fun initRecyclerView() {
        rv_history.apply {
            adapter = historyAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        rv_advice_client.apply {
            adapter = adviceAdapter
            layoutManager = LinearLayoutManager(context)
        }
        rv_orders_by_category.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                TWO,
                GridLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun updateFeeds() {
        viewModel.getCategory()
        viewModel.getAdvice()
        viewModel.getHistory()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(HomeSpecialistViewModel::class.java)
    }

    private fun initListeners() {
        btn_find_order.onClick {
            navigateTo(R.id.mapSpecialistFragment)
        }
    }

    private fun initObservers() {
        viewModel.categoryList.observe(viewLifecycleOwner, {
            setCategoryList(it)
        })
        viewModel.adviceList.observe(viewLifecycleOwner, {
            setAddViceList(it)
        })
        viewModel.historyList.observe(viewLifecycleOwner, {
            setHistoryList(it)
        })
    }

    private fun setHistoryList(it: List<HistoryResponse>?) {
        historyAdapter.addList(it!!)
    }

    private fun setAddViceList(adviceList: List<AdviceResponse>?) {
        adviceAdapter.addList(adviceList!!)
    }

    private fun setCategoryList(categoryList: List<CategoryResponse>?) {
        categoryAdapter.addList(categoryList!!)
    }

    private fun setLoading(loading: Boolean) {

    }

}