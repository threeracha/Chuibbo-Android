package com.example.chuibbo_android.utils

import android.app.Activity
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.renderscript.ScriptGroup
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.content.contentValuesOf
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.chuibbo_android.R
import com.example.chuibbo_android.camera.ConfirmFragment
import java.io.*
import java.lang.UnsupportedOperationException
import java.text.SimpleDateFormat
import java.time.LocalTime
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

    fun saveBitmapToJpeg(bitmap: Bitmap, name: String) {
        val storage: File = activity?.cacheDir!!
        val fileName = "$name.jpg"

        val tempFile = File(storage, fileName)
        try {
            tempFile.createNewFile()
            val out = FileOutputStream(tempFile)
            // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.close()
        } catch (e: FileNotFoundException) {
            Log.e("MyTag", "FileNotFoundException : " + e.message)
        } catch (e: IOException) {
            Log.e("MyTag", "IOException : " + e.message)
        }
    }

    fun saveBitmapToGallery(bitmap: Bitmap, name: String) {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$name.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/*")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        val contentResolver = activity?.applicationContext.contentResolver
        var collection: Uri ?= getImageUri(activity?.applicationContext, bitmap)

    // FIXME: 기능은 정상작동 되나 아래 코드가 왜 없어도 되는지 공부 필요..
        /*
        if (collection != null) {

            var item = contentResolver.insert(collection, values)!!

            if (isExternalStorageWritable()) {
                try {
                    contentResolver.openFileDescriptor(item!!, "w", null).use { pfd ->
                        val bytes = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                        val bitmapData = bytes.toByteArray()
                        val bs = ByteArrayInputStream(bitmapData)
                        val fos = FileOutputStream(pfd?.fileDescriptor)
                        val byteBuffer = ByteArrayOutputStream()
                        val bufferSize = 1024;
                        val buffer = ByteArray(bufferSize)
                        var len = 0;
                        while ((bs.read(buffer).also { len = it }) != -1) {
                            byteBuffer.write(buffer, 0, len)
                        }
                        fos.write(byteBuffer.toByteArray())
                        fos.close()
                        bs.close()
                        pfd?.close()
                    }
                    Log.d("wow6", "wow")
                    contentResolver.update(item, values, null, null)
                } catch (e: FileNotFoundException) {
                    Log.e("MyTag", "FileNotFoundException : " + e.message)
                } catch (e: IOException) {
                    Log.e("MyTag", "IOException : " + e.message)
                } finally {
                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    contentResolver.update(item!!, values, null, null)
                }
            } else {
                Toast.makeText(activity?.applicationContext, "외부저장소 엑세스 접근 실패", Toast.LENGTH_SHORT).show()
            }
        }*/
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap?): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext?.contentResolver, inImage, "finalResult", null)
        return Uri.parse(path)
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}
