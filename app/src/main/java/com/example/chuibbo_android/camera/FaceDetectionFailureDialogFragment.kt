package com.example.chuibbo_android.camera

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.chuibbo_android.R
import com.example.chuibbo_android.camera.CameraFragment.Companion.isFromGallery
import kotlinx.android.synthetic.main.face_detection_failfure_dialog_fragment.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FaceDetectionFailureDialogFragment : DialogFragment() {
    private lateinit var currentPhotoPath: String

    val requestCameraActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        setFragmentResult("requestKey", bundleOf("bundleKey" to currentPhotoPath))
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.frameLayout, ConfirmFragment())
        transaction.commit()
    }

    private val requestGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        setFragmentResult("requestKey", bundleOf("bundleKeyUri" to activityResult.data!!.data))
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.frameLayout, ConfirmFragment())
        transaction.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.face_detection_failfure_dialog_fragment, container, false)

        var fragment: Fragment? = activity?.supportFragmentManager?.findFragmentByTag("Face Detection Failure")

        view.btn_dialog_cancel2.setOnClickListener {
            var dialogFragment: DialogFragment = fragment as DialogFragment
            dialogFragment.dismiss()
        }

        view.btn_dialog_again.setOnClickListener {
            when (isFromGallery) {
                true -> {
                    Intent(Intent.ACTION_PICK).also { mediaScanIntent ->
                        mediaScanIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                        requestGalleryActivity.launch(mediaScanIntent)
                    }
                }
                false -> {
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                        activity?.packageManager?.let {
                            takePictureIntent.resolveActivity(it)?.also {
                                val photoFile: File? = try {
                                    createImageFile()
                                } catch (ex: IOException) {
                                    // Error occurred while creating the File
                                    Log.d("test", "error: $ex")
                                    null
                                }
                                var photoURI: Uri? = null
                                // photoUri를 보내는 코드
                                photoFile?.also {
                                    activity?.also {
                                        photoURI = FileProvider.getUriForFile(it, "com.example.chuibbo_android.camera", photoFile)
                                    }
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                                    requestCameraActivity.launch(takePictureIntent)
                                }
                            }
                        }
                    }
                }
            }
        }
        return view
    }

    fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        return image
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}
