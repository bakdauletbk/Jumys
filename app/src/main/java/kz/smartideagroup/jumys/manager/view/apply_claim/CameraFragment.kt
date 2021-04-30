package kz.smartideagroup.jumys.manager.view.apply_claim

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import kotlinx.android.synthetic.main.fragment_camera.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.views.BaseFragment

class CameraFragment : BaseFragment(R.layout.fragment_camera) {

    private var videoUri: Uri? = null

    companion object {
        const val VIDEO_REQUEST = 101
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initListeners()
        initObservers()
        playVideo()
    }

    private fun playVideo() {
        video_view.setVideoPath(videoUri.toString())
        video_view.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK) {
            true -> {
                videoUri = data?.data
            }
        }
    }

    private fun initViewModel() {

    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }

}