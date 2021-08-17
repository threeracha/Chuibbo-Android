package com.example.chuibbo_android.correction

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.MakeupApi
import com.example.chuibbo_android.api.request.MakeupRequest
import com.example.chuibbo_android.api.request.MakeupStrongRequest
import com.example.chuibbo_android.download.DownloadFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.face_correction_fragment.*
import kotlinx.android.synthetic.main.face_correction_makeup_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream
import java.util.HashMap

class FaceCorrectionFragment : Fragment(), IUploadCallback {
    private lateinit var file: File
    private val makeupService = MakeupApi.instance

    private fun changeView(index: Int) {
        when (index) {
            0 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.correction_contents, FaceCorrectionInsideFragment())
                }?.commit()
            }
            1 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.correction_contents, FaceCorrectionMakeupFragment())
                }?.commit()
            }
            2 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.correction_contents, FaceCorrectionDistortionFragment())
                }?.commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.face_correction_fragment, container, false)

        activity?.toolbar_title!!.text = ""
        activity?.back_button!!.visibility = View.VISIBLE
        activity?.process3!!.visibility = View.VISIBLE
        activity?.btn_next!!.visibility = View.VISIBLE

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.correction_contents, FaceCorrectionInsideFragment())
        }?.commit()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        file = File(activity?.cacheDir?.toString()+"/result.jpg")
        uploadFile()
        activity?.img_face_correction?.setImageBitmap(BitmapFactory.decodeFile(activity?.cacheDir?.toString()+"/result.jpg"))

        activity?.btn_next!!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, DownloadFragment())
                addToBackStack(null)
            }?.commit()
        }

        tablayout_face_correction.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val pos = tab.getPosition()
                changeView(pos)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // do nothing
            }
        })
    }

    //서버로 이미지 업로드
    private fun uploadFile() {
        if (file != null) {
            val requestBody = ProgressRequestBody(file, this)
            val body = MultipartBody.Part.createFormData("image", file.name, requestBody)

            Thread(Runnable {
                makeupService.uploadFile(body)
                    .enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            // image_view.setImageURI(selectedUri)
                            Log.d("결과", "성공 : //{response.raw()}")
                            Toast.makeText(context, "사진 전송에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                        }

                    })
            }).start()
        } else {
            Toast.makeText(context, "이 이미지를 업로드 할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
        activity?.process3!!.visibility = View.GONE
        activity?.btn_next!!.visibility = View.GONE
    }

    override fun onProgressupdate(percent: Int) {

    }
}
