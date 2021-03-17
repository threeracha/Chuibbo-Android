package com.example.chuibbo_android.camera

import android.content.Intent
import android.graphics.Bitmap
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.confirm_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ConfirmFragment : Fragment() {

    private val requestGalleryActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        setFragmentResult("requestKey", bundleOf("bundleKeyUri" to activityResult.data!!.data))
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.frameLayout, ConfirmFragment())
        clearBackStack()
        transaction.commit()
    }

    private fun clearBackStack() {
        val fragmentManager: FragmentManager = activity?.supportFragmentManager!!
        while (fragmentManager.backStackEntryCount !== 0) {
            fragmentManager.popBackStackImmediate()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var result : String ?= null
        var resultUri : Uri ?= null
        setFragmentResultListener("requestKey") { key, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            if (bundle.getString("bundleKey") != null) {
                result = bundle.getString("bundleKey")
                BitmapFactory.decodeFile(result)?.also { bitmap ->
                    img_preview.setImageBitmap(bitmap)
                }
            } else if (bundle.getParcelable<Uri>("bundleKeyUri") != null) {
                resultUri = bundle.getParcelable("bundleKeyUri")
                img_preview.setImageURI(resultUri)
            }
        }
        return inflater.inflate(R.layout.confirm_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar!!.setTitle("사진 선택")

        btn_cancel.setOnClickListener {
            galleryAddPic()
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
                requestGalleryActivity.launch(mediaScanIntent)
            }
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
        //currentPhotoPath = image.absolutePath
        return image
    }

    // TODO: 뒤로가기 누르면 이전 액티비티 시행

   // TODO: 이미지 resize 할 size 정하기 & 함수 적용
    // ImageView에 사진을 넣는 메소드
    private fun setPic(photoPath : String){
        var img : ImageView = img_preview

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