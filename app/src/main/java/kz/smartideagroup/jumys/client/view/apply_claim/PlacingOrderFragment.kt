package kz.smartideagroup.jumys.client.view.apply_claim

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_placing_order.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.FOUR
import kz.smartideagroup.jumys.common.utils.MP4
import kz.smartideagroup.jumys.common.views.BaseFragment
import kz.smartideagroup.jumys.client.model.request.apply_claim.RequestWorkUpload
import kz.smartideagroup.jumys.client.view.apply_claim.adapter.MediaAdapter
import kz.smartideagroup.jumys.client.viewmodel.apply_claim.CameraCommonViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick

class PlacingOrderFragment : BaseFragment(R.layout.fragment_placing_order) {

    private lateinit var commonViewModel: CameraCommonViewModel
    private var alertDialog: Dialog? = null
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
        commonViewModel.isError.observe(viewLifecycleOwner, {
            setLoading(false)
            errorDialog(getString(R.string.error_no_internet_msg))
        })
        commonViewModel.isSuccess.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                }
                false -> {
                    setLoading(false)
                    errorDialog(getString(R.string.error_failed_connection_to_server))
                }
            }
        })
        commonViewModel.mediaList.observe(viewLifecycleOwner, {
            Log.d("ErmahanList", it.toString())
            mediaAdapter.addList(it)
        })
        commonViewModel.sum.observe(viewLifecycleOwner, {
            et_sum.setText(it)
        })
        commonViewModel.videoBase.observe(viewLifecycleOwner, {
            Log.d("ErmahanVideoBase", "asdas")
            Log.d("ErmahanVideoBase", it.toString())
            val requestWorkUpload = RequestWorkUpload( work = 2,video = it.toString())
            setLoading(true)
            CoroutineScope(Dispatchers.IO).launch {
                commonViewModel.setVideoUpload(requestWorkUpload)
            }
            Log.d("ErmahanVideoBase", "easa")

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
            layoutManager = GridLayoutManager(
                requireContext(),
                FOUR,
                GridLayoutManager.VERTICAL,
                false
            )
        }
    }

    fun navigateToCamera() {
        commonViewModel.setSum(et_sum.text.toString())
        navigateTo(R.id.cameraFragment)
    }

    fun removeItemMediaList(position: Int) {
        commonViewModel.removeItem(position)
        navigateTo(R.id.placingOrderFragment)
    }

    fun previewMediaAlert(path: String) {
        alertDialog = Dialog(requireContext())
        alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog!!.setContentView(R.layout.alert_dialog_preview_media)

        val llCancel = alertDialog!!.findViewById<RelativeLayout>(R.id.ll_close)
        val imagePreview = alertDialog!!.findViewById<ImageView>(R.id.image_preview)
        val videoPreview = alertDialog!!.findViewById<VideoView>(R.id.videoView)

        when (path.takeLast(FOUR) == MP4) { // Mp4 and Jpg format
            true -> {
                imagePreview.visibility = View.GONE
                videoPreview.visibility =
                    View.VISIBLE

                // ???????????????? MediaController
                val mediaController = MediaController(requireContext())
                mediaController.setAnchorView(mediaController)
                videoPreview.setMediaController(mediaController)
                videoPreview.setVideoURI(Uri.parse(path))
                videoPreview.requestFocus()
                videoPreview.start()
            }
            false -> {
                videoPreview.visibility =
                    View.GONE
                imagePreview.visibility = View.VISIBLE
                Glide.with(requireActivity()).load(path).into(imagePreview)
            }
        }

        llCancel.onClick {
            alertDialog!!.cancel()
        }

        alertDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        alertDialog!!.show()

        val window: Window = alertDialog!!.window!!
        window.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    private fun setLoading(loading: Boolean) {
        loadingView.visibility = if (loading) View.VISIBLE else View.GONE
    }

}