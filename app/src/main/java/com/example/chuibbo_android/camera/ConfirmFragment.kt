package com.example.chuibbo_android.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.confirm_fragment.*

class ConfirmFragment : Fragment() {
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