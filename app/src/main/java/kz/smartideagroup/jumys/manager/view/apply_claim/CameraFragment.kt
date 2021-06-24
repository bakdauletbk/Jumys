package kz.smartideagroup.jumys.manager.view.apply_claim

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
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
import androidx.exifinterface.media.ExifInterface
import kotlinx.android.synthetic.main.fragment_camera.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.*
import kz.smartideagroup.jumys.common.views.BaseFragment
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

    private var isRecording = false
    private val videoCapture = VideoCapture.Builder().build()
    private var imageCapture = ImageCapture.Builder().build()
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var mCountDownTimer: CountDownTimer? = null
    private val bundle = Bundle()

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

//                    bundle.putString(PUT_SAVED_URI, savedUri.toString())
                    readFileFromInternalStorage(savedUri.toString())

//                    requireActivity().findNavController(R.id.container)
//                        .navigate(R.id.placingOrderFragment, bundle)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                if (requestCode == SELECT_PHOTO) {
                    val selectImage = data?.data
                    var inputStream: InputStream? = null
                    try {
                        inputStream = selectImage?.let {
                            activity?.contentResolver?.openInputStream(
                                it
                            )
                        }
                    } catch (e: FileNotFoundException) {
                        Log.d("ErmahanAc", e.toString())
                    }
                    BitmapFactory.decodeStream(inputStream)
                    Log.d("ErmahanAc", selectImage?.path.toString())
                    iv_image_test.setImageURI(selectImage)
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
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = IMAGE
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
        cv_gallery.onClick {
            openGallery()
        }
    }

    private fun initObservers() {}

    private fun readFileFromInternalStorage(path: String) {
        iv_image_test.visibility = View.VISIBLE

        val pathName = path.substring(SEVEN, path.length)

        var bitmap =
            BitmapFactory.decodeFile(pathName)

        try {
            val exif =
                ExifInterface(pathName)
            val orientation: Int = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ONE)

            val matrix = Matrix()
            when (orientation) {
                SIX -> matrix.postRotate(DEGREES_90)
                THREE -> matrix.postRotate(DEGREES_180)
                EIGHT -> matrix.postRotate(DEGREES_270)
            }
            bitmap = Bitmap.createBitmap(
                bitmap,
                ZERO,
                ZERO,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            ) // rotating bitmap
        } catch (e: java.lang.Exception) {
        }

        iv_image_test.setImageBitmap(bitmap)
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