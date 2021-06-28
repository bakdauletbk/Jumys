package kz.smartideagroup.jumys.manager.view.apply_claim

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_placing_order.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.PUT_SAVED_URI
import kz.smartideagroup.jumys.common.views.BaseFragment
import kz.smartideagroup.jumys.manager.view.apply_claim.adapter.MediaAdapter

class PlacingOrderFragment : BaseFragment(R.layout.fragment_placing_order) {

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
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        val media = arguments?.getString(PUT_SAVED_URI) as String
        Log.d("Ermahans", media)
        mediaList.add(media)
        mediaList.add(media)
        mediaList.add(media)
        mediaList.add("")// Не уберать это нужен last item recycler view
        mediaAdapter.addList(mediaList)
    }

    private fun initListeners() {

    }

    private fun initViewModel() {

    }

    private fun initRecyclerView() {
        rv_media.apply {
            adapter = mediaAdapter
            layoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
        }
    }
}