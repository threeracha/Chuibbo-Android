package com.example.chuibbo_android.camera

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.camera_activity.*
import kotlinx.android.synthetic.main.confirm_activity.*

class CameraActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)

        camera_capture_button.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            data?.let{
                println("not not null null")
                val imageBitmap = data!!.extras.get("data") as Bitmap
                val intent = Intent(this, ConfirmActivity::class.java)
                intent.putExtra("img_preview", imageBitmap)
                startActivity(intent)
            } ?: run {
                println("null null null")
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA = 100
    }
}