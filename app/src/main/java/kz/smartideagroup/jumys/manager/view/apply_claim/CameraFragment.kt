package kz.smartideagroup.jumys.manager.view.apply_claim

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_camera.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.*
import kz.smartideagroup.jumys.common.views.BaseFragment
import kz.smartideagroup.jumys.manager.viewmodel.apply_claim.CameraCommonViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : BaseFragment(R.layout.fragment_camera) {

    companion object {
        private const val TAG = "CameraXBasic"
    }

    private lateinit var commonViewModel: CameraCommonViewModel
    private var isRecording = false
    private val videoCapture = VideoCapture.Builder().build()
    private var imageCapture = ImageCapture.Builder().build()
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var mCountDownTimer: CountDownTimer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initPermissions()
        initViewModel()
        initListeners()
        initObservers()
    }

    private fun initPermissions() {
        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRecording()
        cameraExecutor.shutdown()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs?.firstOrNull().let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir.exists())
            mediaDir else requireActivity().filesDir
    }

    private fun takePhoto() {
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + JPG
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    readFileFromInternalStorage(savedUri.toString())
                    val pathName = savedUri.toString().substring(SEVEN, savedUri.toString().length)
                    commonViewModel.setMediaArrayList(pathName)
                    navigateTo(R.id.placingOrderFragment)
                    Log.d(TAG, msg)
                }
            })

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, videoCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SELECT_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    val selectImage = data?.data
                    var inputStream: InputStream? = null
                    try {
                        inputStream = selectImage?.let {
                            activity?.contentResolver?.openInputStream(
                                it
                            )
                        }
                    } catch (e: FileNotFoundException) {
                    }
                    BitmapFactory.decodeStream(inputStream)
                    val pathName = selectImage?.path.toString()
                        .substring(FIVE, selectImage?.path.toString().length)
                    commonViewModel.setMediaArrayList(pathName)
                    navigateTo(R.id.placingOrderFragment)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                if (allPermissionsGranted()) {
                    startCamera()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            REQUEST_RECORD_AUDIO_PERMISSION -> {
                grantResults[ZERO] = PackageManager.PERMISSION_GRANTED
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        intent.type = (IMAGE_VIDEO)
        activity?.startActivityFromFragment(this, intent, SELECT_PHOTO)
    }

    @SuppressLint("RestrictedApi")
    private fun startRecording() {
        val videoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + MP4
        )

        val outputOptions = VideoCapture.OutputFileOptions.Builder(videoFile).build()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        videoCapture.startRecording(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : VideoCapture.OnVideoSavedCallback {
                override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                    Log.e("Video", "Video capture failed: $message")
                }

                override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(videoFile)
                    val pathName = savedUri.toString().substring(SEVEN, savedUri.toString().length)
                    readFileFromInternalStorage(savedUri.toString())
                    commonViewModel.setMediaArrayList(pathName)
                    navigateTo(R.id.placingOrderFragment)
                }
            })
    }

    @SuppressLint("RestrictedApi")
    private fun stopRecording() {
        videoCapture.stopRecording()
    }

    override fun onPause() {
        pb_recording_timer.visibility = View.GONE
        super.onPause()
    }

    private fun initViewModel() {
        commonViewModel = ViewModelProvider(requireActivity())[CameraCommonViewModel::class.java]
    }

    private fun initListeners() {
        btn_camera_capture.setOnClickListener {
            takePhoto()
        }
        btn_camera_capture.setOnLongClickListener {
            isRecording = when (isRecording) {
                true -> {
                    stopRecording()
                    false
                }
                false -> {
                    startTimer()
                    startRecording()
                    true
                }
            }
            true
        }
        cv_gallery.onClick {
            openGallery()
        }
    }

    private fun initObservers() {}

    private fun readFileFromInternalStorage(path: String) {
        iv_image_test.visibility = View.VISIBLE
        val pathName = path.substring(SEVEN, path.length)
        Glide.with(requireActivity()).load(pathName).into(iv_image_test)
    }

    private fun startTimer() {
        var i = ZERO

        pb_recording_timer.progress = i
        pb_recording_timer.visibility = View.VISIBLE
        mCountDownTimer = object : CountDownTimer(FIFTEEN, COUNT_DOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                try {
                    i++
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        pb_recording_timer.setProgress(i, true)
                    } else {
                        pb_recording_timer.progress = i
                    }
                } catch (e: Exception) {
                    mCountDownTimer?.onFinish()
                }
            }

            override fun onFinish() {
                when (pb_recording_timer != null) {
                    true -> {
                        pb_recording_timer.visibility = View.GONE
                    }
                }
                stopRecording()
            }
        }.start()
    }


}