package com.example.chuibbo_android.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.BackgroundApi
import com.example.chuibbo_android.api.response.FlaskServerResponse
import com.example.chuibbo_android.background.BackgroundSynthesisFragment
import com.example.chuibbo_android.image.Adapter
import com.example.chuibbo_android.image.Image
import com.example.chuibbo_android.image.ImageViewModel
import com.example.chuibbo_android.utils.Common
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.overall_synthesis_confirm_item.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SynthesisConfirmFragment : Fragment() {
    private lateinit var vm: ImageViewModel
    private lateinit var filePath: String
    private val adapter = Adapter()
    private lateinit var result: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.back_button?.visibility = View.VISIBLE
        activity?.process1?.visibility = View.VISIBLE
        activity?.btn_next?.visibility = View.VISIBLE

        return inflater.inflate(R.layout.overall_synthesis_confirm_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val common = Common(this, this.requireActivity())

        activity?.toolbar_title!!.text = ""

        vm = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ImageViewModel::class.java)

        // TODO: 2021/03/29 Recycler View Binding 아닌, ImageView 하나의 Object에 바인딩 하
        // 리사이클러뷰 적용 보류
//        recycerlview_img.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            adapter = ad
//        }

        filePath = activity?.cacheDir!!.toString() + "/result.jpg"
        result = BitmapFactory.decodeFile(filePath)
        img_synthesis!!.setImageBitmap(result)

        activity?.btn_next?.setOnClickListener {
            val fileBody = File(filePath).asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("photo", "photo.jpg", fileBody)
            val idBody = "sss20_0".toRequestBody(("text/plain").toMediaTypeOrNull())

            // 로컬DB에 이미지 데이터 저장
            var data = Image(0, filePath)
            // vm.delete()
            vm.insert(data)
            Log.d("item count = ", adapter.itemCount.toString())

            var options = hashMapOf("id" to idBody)

            // request resume photo to server in a coroutine scope
            runBlocking {
                BackgroundApi.instance.removeBackground(
                    filePart,
                    data = options
                ).enqueue(object : Callback<FlaskServerResponse> {
                    override fun onFailure(call: Call<FlaskServerResponse>, t: Throwable) {
                        Log.d("retrofit fail", t.message)
                    }

                    override fun onResponse(
                        call: Call<FlaskServerResponse>,
                        response: Response<FlaskServerResponse>
                    ) {
                        if (response.isSuccessful) {
                            when (response.body()?.code) {
                                1 -> {
                                    val decode_img = Base64.decode(response.body()?.data, Base64.DEFAULT)
                                    val bitmapResultImage = BitmapFactory.decodeByteArray(decode_img, 0, decode_img.size)

                                    common.saveBitmapToJpeg(bitmapResultImage, "result2")

                                    val transaction = activity?.supportFragmentManager!!.beginTransaction()
                                    transaction.replace(R.id.frameLayout, BackgroundSynthesisFragment())
                                    transaction.addToBackStack("camera")
                                    transaction.commitAllowingStateLoss()
                                }
                            }
                        }
                    }
                })
            }
        }

        vm.allItem.observe(viewLifecycleOwner) { adapter.addCategoryList(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
        activity?.process1!!.visibility = View.GONE
        activity?.btn_next!!.visibility = View.GONE
    }
}
