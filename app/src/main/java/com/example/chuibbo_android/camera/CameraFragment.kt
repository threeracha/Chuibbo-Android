package com.example.chuibbo_android.camera

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.camera_fragment.*
import kotlinx.android.synthetic.main.confirm_fragment.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment : Fragment() {
    private lateinit var currentPhotoPath: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        gallery_capture_button.setOnClickListener { galleryAddPic() }
        camera_capture_button.setOnClickListener { dispatchTakePictureIntent() }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity?.packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.d("test", "error: $ex")
                    null
                }
                var photoURI : Uri? = null
                // photoUri를 보내는 코드
                photoFile?.also {
                    activity?.also {
                        photoURI = FileProvider.getUriForFile(it, "com.example.chuibbo_android.camera", photoFile)
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA)
                }
            }
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_PICK).also { mediaScanIntent ->
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                Log.d("test", "error: $ex")
                null
            }
            var photoURI : Uri? = null
            photoFile?.also {
                activity?.also {
                    photoURI = FileProvider.getUriForFile(it, "com.example.chuibbo_android.camera", photoFile)
                }
                mediaScanIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                startActivityForResult(mediaScanIntent, CHOOSE_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CAMERA && resultCode == AppCompatActivity.RESULT_OK) {
            setFragmentResult("requestKey", bundleOf("bundleKey" to currentPhotoPath))
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.frameLayout, ConfirmFragment())
            transaction.commit()
        }
        else if(requestCode == CHOOSE_IMAGE && resultCode == AppCompatActivity.RESULT_OK) {
            setFragmentResult("requestKey", bundleOf("bundleKeyUri" to data!!.data))
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.frameLayout, ConfirmFragment())
            transaction.commit()
        }
    }

    // 사진 파일을 만드는 메소드
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        return image
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val REQUEST_CAMERA = 100
        private const val CHOOSE_IMAGE = 1001
    }
}