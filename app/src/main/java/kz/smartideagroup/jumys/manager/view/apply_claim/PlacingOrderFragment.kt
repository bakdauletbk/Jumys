package kz.smartideagroup.jumys.manager.view.apply_claim

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_placing_order.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.PUT_SAVED_URI
import kz.smartideagroup.jumys.common.views.BaseFragment
import kz.smartideagroup.jumys.manager.view.apply_claim.adapter.MediaAdapter
import kz.smartideagroup.jumys.manager.viewmodel.apply_claim.CameraCommonViewModel

class PlacingOrderFragment : BaseFragment(R.layout.fragment_placing_order) {

    private lateinit var commonViewModel: CameraCommonViewModel
    private var mediaList = ArrayList<String>()

    private val mediaAdapter: MediaAdapter =
        MediaAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initRecyclerView()
        initViewModel()
        getMediaList()
        initListeners()
        initObservers()
    }

    private fun getMediaList() {
//        val media = arguments?.getString(PUT_SAVED_URI) as String
//        Log.d("Ermahans", media)
//        mediaList.add(media)
//        commonViewModel.setMedia(media)
//        mediaList.add("")// Не уберать это нужен last item recycler view
//        mediaAdapter.addList(mediaList)
    }

    private fun initObservers() {
        commonViewModel.mediaList.observe(viewLifecycleOwner, {
            Log.d("Erma", "initObservers: $it")
            mediaList.addAll(it)
            mediaAdapter.addList(mediaList)
        })
    }

    private fun initListeners() {

    }

    private fun initViewModel() {
        commonViewModel = ViewModelProvider(requireActivity())[CameraCommonViewModel::class.java]
    }

    private fun initRecyclerView() {
        rv_media.apply {
            adapter = mediaAdapter
            layoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
        }
    }

    fun navigateToCamera() {
        navigateTo(R.id.navigation)
    }

}