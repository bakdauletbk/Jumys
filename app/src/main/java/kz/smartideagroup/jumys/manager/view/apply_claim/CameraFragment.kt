package kz.smartideagroup.jumys.manager.view.apply_claim

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
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
import kotlinx.android.synthetic.main.fragment_camera.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.COUNT_DOWN_INTERVAL
import kz.smartideagroup.jumys.common.utils.FIFTEEN
import kz.smartideagroup.jumys.common.utils.ZERO
import kz.smartideagroup.jumys.common.views.BaseFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : BaseFragment(R.layout.fragment_camera) {

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
        private const val MP4 = ".mp4"
        private const val JPG = ".jpg"
    }

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
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
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
                    val msg = "Video capture succeeded: $savedUri"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    Log.d("Video", msg)
                }
            })
    }

    @SuppressLint("RestrictedApi")
    private fun stopRecording() {
        videoCapture.stopRecording()
    }

    private fun initViewModel() {}

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
    }

    private fun initObservers() {}

    private fun readFileFromInternalStorage(path: String) {
        val cw = ContextWrapper(context)
        //path to /data/data/yourapp/app_data/dirName
        val directory: File = cw.getDir("dirName", Context.MODE_PRIVATE)
        val mypath = File(directory, path)
        iv_image_test.setImageDrawable(Drawable.createFromPath(mypath.toString()))
    }

    private fun startTimer() {
        var i = ZERO

        pb_recording_timer.progress = i

        mCountDownTimer = object : CountDownTimer(FIFTEEN, COUNT_DOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                pb_recording_timer.visibility = View.VISIBLE
                i++
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    pb_recording_timer.setProgress(i, true)
                } else {
                    pb_recording_timer.progress = i
                }
            }

            override fun onFinish() {
                stopRecording()
                pb_recording_timer.visibility = View.GONE
            }

        }.start()
    }


}