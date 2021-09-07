package com.example.chuibbo_android.background

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.BackgroundApi
import com.example.chuibbo_android.api.response.ResumePhotoUploadResponse
import com.example.chuibbo_android.correction.FaceCorrectionFragment
import com.example.chuibbo_android.image.Image
import com.example.chuibbo_android.image.ImageViewModel
import com.example.chuibbo_android.utils.Common
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.background_synthesis_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class BackgroundSynthesisFragment : Fragment() {
    private lateinit var vm: ImageViewModel
    private lateinit var filePath: String
    private lateinit var result: Bitmap
    private lateinit var defaultFilePath: String
    private lateinit var defaultResult: Bitmap
    private lateinit var hexColor: String
    private lateinit var rgbColor: String
    private var gradationNumber = 0
    private var synthesisType: Int = 2 // default background

    @SuppressLint("ResourceAsColor")
    private fun changeView(index: Int) {
        when (index) {
            0 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.color_contents, BackgroundSynthesisSolidcolorFragment())
                }?.commit()
            }
            1 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.color_contents, BackgroundSynthesisGradationFragment())
                }?.commit()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val common = Common(this, this.requireActivity())

        setFragmentResultListener("Picked Color") { resultKey, bundle ->
            synthesisType = 0
            hexColor = bundle.getString("color").toString()
            background_gradation_image.setImageResource(0)
            background_synthesis_image.setBackgroundColor(Color.parseColor("#" + hexColor))

            if (hexColor == "00FFFFFF") {
                synthesisType = 2 // default background
            } else {
                // hex to rgb
                rgbColor = Integer.valueOf(hexColor.substring(2, 4), 16)
                    .toString() + ", " + Integer.valueOf(hexColor.substring(4, 6), 16)
                    .toString() + ", " + Integer.valueOf(hexColor.substring(6, 8), 16).toString()
                // TODO: 투명도 적용
            }
        }

        setFragmentResultListener("Picked Gradation") { resultKey, bundle ->
            synthesisType = 1
            gradationNumber = bundle.get("gradation") as Int
            background_synthesis_image.setBackgroundColor(Color.parseColor("#00FFFFFF"))
            background_gradation_image.setImageResource(gradationNumber)

            if (gradationNumber == 0) {
                synthesisType = 2 // default background
            } else {
                val bitmapImage = BitmapFactory.decodeResource(context?.resources, gradationNumber)
                val fileName = "gradation"
                common.saveBitmapToJpeg(bitmapImage, fileName)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.background_synthesis_fragment, container, false)

        activity?.toolbar_title!!.text = ""
        activity?.back_button!!.visibility = View.VISIBLE
        activity?.process2!!.visibility = View.VISIBLE
        activity?.btn_next!!.visibility = View.VISIBLE

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.color_contents, BackgroundSynthesisSolidcolorFragment())
        }?.commit()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val common = Common(this, this.requireActivity())

        vm = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            ImageViewModel::class.java)

        setFragmentResultListener("requestRembgKey") { key, bundle ->
            filePath = bundle.getString("bundleRembgPathKey")!!
            result = bundle.getParcelable<Bitmap>("bundleRembgBitmapKey")!!
            background_synthesis_image!!.setImageBitmap(result)
            defaultFilePath = bundle.getString("bundleDefaultPathKey")!!
            defaultResult = bundle.getParcelable<Bitmap>("bundleDefaultBitmapKey")!!
        }

        activity?.btn_next!!.setOnClickListener {
            val fileBody = File(filePath).asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("photo", "photo.jpg", fileBody)
            val idBody = "sss20_0".toRequestBody(("text/plain").toMediaTypeOrNull())

            // 로컬DB에 이미지 데이터 저장
            var data = Image(0, filePath)
            vm.insert(data)

            var options = hashMapOf("id" to idBody)

            if (synthesisType == 0) {
                val solidColorBody = rgbColor.toRequestBody(("text/plain").toMediaTypeOrNull())

                options.put("solid_color", solidColorBody)

                // request color to server in a coroutine scope
                runBlocking {
                    BackgroundApi.instance.backgroundSynthesisSolid(
                        filePart,
                        data = options
                    ).enqueue(object : Callback<ResumePhotoUploadResponse> {
                        override fun onFailure(call: Call<ResumePhotoUploadResponse>, t: Throwable) {
                            Log.d("retrofit fail", t.message)
                        }

                        override fun onResponse(
                            call: Call<ResumePhotoUploadResponse>,
                            response: Response<ResumePhotoUploadResponse>
                        ) {
                            if (response.isSuccessful) {
                                val result = response.body()?.code
                                when (result) {
                                    1 -> {
                                        val decode_img = Base64.decode(response.body()?.data, Base64.DEFAULT)
                                        val bitmapResultImage = BitmapFactory.decodeByteArray(decode_img, 0, decode_img.size)

                                        val fileName = "result3"
                                        common.saveBitmapToJpeg(bitmapResultImage, fileName)

                                        val path = activity?.cacheDir!!.toString() + "/" + fileName + ".jpg"
                                        setFragmentResult("requestBackgroundKey", bundleOf("bundleBackgroundBitmapKey" to bitmapResultImage,
                                            "bundleBackgroundPathKey" to path))
                                        val transaction = activity?.supportFragmentManager!!.beginTransaction()
                                        transaction.replace(R.id.frameLayout, FaceCorrectionFragment())
                                        transaction.commitAllowingStateLoss()
                                    }
                                }
                            }
                        }
                    })
                }
            } else if (synthesisType == 1) {
                val filePath2 = activity?.cacheDir!!.toString() + "/" + "gradation" + ".jpg"

                val fileBody2 = File(filePath2).asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val filePart2 = MultipartBody.Part.createFormData("gradation_photo", "gradation.jpg", fileBody2)

                var data = Image(1, filePath2)
                vm.insert(data)

                // request color to server in a coroutine scope
                runBlocking {
                    BackgroundApi.instance.backgroundSynthesisGradation(
                        filePart,
                        data = options,
                        filePart2
                    ).enqueue(object : Callback<ResumePhotoUploadResponse> {
                        override fun onFailure(call: Call<ResumePhotoUploadResponse>, t: Throwable) {
                            Log.d("retrofit fail", t.message)
                        }

                        override fun onResponse(
                            call: Call<ResumePhotoUploadResponse>,
                            response: Response<ResumePhotoUploadResponse>
                        ) {
                            if (response.isSuccessful) {
                                val result = response.body()?.code
                                when (result) {
                                    1 -> {
                                        val decode_img = Base64.decode(response.body()?.data, Base64.DEFAULT)
                                        val bitmapResultImage = BitmapFactory.decodeByteArray(decode_img, 0, decode_img.size)

                                        val fileName = "result1"
                                        common.saveBitmapToJpeg(bitmapResultImage, fileName)

                                        val path = activity?.cacheDir!!.toString() + "/" + fileName + ".jpg"
                                        setFragmentResult("requestBackgroundKey", bundleOf("bundleBackgroundBitmapKey" to bitmapResultImage,
                                            "bundleBackgroundPathKey" to path))

                                        val transaction = activity?.supportFragmentManager!!.beginTransaction()
                                        transaction.replace(R.id.frameLayout, FaceCorrectionFragment())
                                        transaction.commitAllowingStateLoss()
                                    }
                                }
                            }
                        }
                    })
                }
            } else { // 아무것도 선택하지 않았을 때, 기본 배경을 설정해준다.
                // TODO: 기본 배경으로 설정해준다는 dialog 띄우기
                setFragmentResult("requestBackgroundKey", bundleOf("bundleBackgroundBitmapKey" to defaultResult,
                    "bundleBackgroundPathKey" to defaultFilePath))
                val transaction = activity?.supportFragmentManager!!.beginTransaction()
                transaction.replace(R.id.frameLayout, FaceCorrectionFragment())
                transaction.commitAllowingStateLoss()
            }
        }

        background_synthesis_image_tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                var pos = tab.getPosition()
                changeView(pos)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
        activity?.process2!!.visibility = View.GONE
        activity?.btn_next!!.visibility = View.GONE
    }
}
