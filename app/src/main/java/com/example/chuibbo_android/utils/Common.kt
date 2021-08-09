package com.example.chuibbo_android.utils

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.chuibbo_android.R
import com.example.chuibbo_android.camera.ConfirmFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Common(
    val fragment: Fragment,
    val activity: Activity
) {
    private lateinit var currentPhotoPath: String

    private val requestCameraActivity = fragment.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        fragment.setFragmentResult("requestKey", bundleOf("bundleKey" to currentPhotoPath))
        val transaction = fragment.requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, ConfirmFragment())
        clearBackStack()
        transaction.commit()
    }

    private val requestGalleryActivity = fragment.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        fragment.setFragmentResult("requestKey", bundleOf("bundleKeyUri" to activityResult.data!!.data))
        val transaction = fragment.requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, ConfirmFragment())
        clearBackStack()
        transaction.commit()
    }

    private fun clearBackStack() { // 조금 더 공부해보자
        val fragmentManager: FragmentManager = fragment.requireActivity().supportFragmentManager
        while (fragmentManager.backStackEntryCount != 2) {
            fragmentManager.popBackStackImmediate()
        }
    }

    private fun createImageFile(): File {
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

    fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            activity?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        Log.d("error", "error: $ex")
                        return
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

    fun dispatchGalleryIntent() {
        Intent(Intent.ACTION_PICK).also { mediaScanIntent ->
            mediaScanIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            requestGalleryActivity.launch(mediaScanIntent)
        }
    }

    // TODO: 이미지 resize 할 size 정하기 & 함수 적용
    // ImageView에 사진을 넣는 메소드
    private fun setPic(
        photoPath: String,
        img: ImageView
    ) {
        // Get the dimensions of the View
        val targetW: Int = img.width
        val targetH: Int = img.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true
            val photoW: Int = outWidth
            val photoH: Int = outHeight
            // Determine how much to scale down the image
            val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        BitmapFactory.decodeFile(photoPath, bmOptions)?.also { bitmap ->
            img.setImageBitmap(bitmap)
        }
    }
}
