package com.example.chuibbo_android.camera

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.camera_activity.*
import kotlinx.android.synthetic.main.confirm_activity.*

class ConfirmActivity :AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_activity)

        if (intent.hasExtra("img_preview")) {
            val img = intent.getParcelableExtra<Bitmap>("img_preview")
            img_preview.setImageBitmap(img)
        }
    }

}