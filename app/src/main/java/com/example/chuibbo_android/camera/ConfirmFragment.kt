package com.example.chuibbo_android.camera

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.ImageApi
import com.example.chuibbo_android.api.response.ResumePhotoUploadResponse
import com.example.chuibbo_android.image.Image
import com.example.chuibbo_android.image.ImageViewModel
import com.example.chuibbo_android.option.Option
import com.example.chuibbo_android.utils.Common
import com.example.chuibbo_android.utils.URIPathHelper
import kotlinx.android.synthetic.main.confirm_fragment.*
import kotlinx.android.synthetic.main.face_detection_failfure_dialog_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ConfirmFragment : Fragment() {
    private lateinit var filePath: String
    private lateinit var vm: ImageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var result: String
        var resultUri: Uri

        vm = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ImageViewModel::class.java)

        setFragmentResultListener("requestKey") { key, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            if (bundle.getString("bundleKey") != null) { // camera
                filePath = bundle.getString("bundleKey")!!
                result = bundle.getString("bundleKey")!!
                BitmapFactory.decodeFile(result)?.also { bitmap ->
                    img_preview.setImageBitmap(bitmap)
                }
            } else if (bundle.getParcelable<Uri>("bundleKeyUri") != null) { // gallery
                val uriPathHelper = URIPathHelper()
                filePath = uriPathHelper.getPath(
                    requireContext(),
                    bundle.getParcelable<Uri>("bundleKeyUri")!!
                ).toString()
                resultUri = bundle.getParcelable("bundleKeyUri")!!
                img_preview.setImageURI(resultUri)
            }
        }
        return inflater.inflate(R.layout.confirm_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar!!.setTitle("사진 선택")

        val common = Common(this, this.requireActivity())
        // FIXME: 2021/03/25 여기서 뒤로가기 버튼 누르면 앱이 종료됨
        btn_select_again.setOnClickListener {
            // TODO: 2021/03/26 스택 이름을 나눠서 지정하여 여기서 꺼내기 
            common.dispatchGalleryIntent()
        }
        btn_confirm.setOnClickListener {
            val fileBody = File(filePath).asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val filePart = createFormData("photo", "photo.jpg", fileBody)
            val idBody = Option.Opt.opt.id.toRequestBody(("text/plain").toMediaTypeOrNull())
            val sexBody = Option.Opt.opt.sex.type.toString().toRequestBody(("text/plain").toMediaTypeOrNull())
            val faceShapeBody = Option.Opt.opt.face_shape.type.toString().toRequestBody(("text/plain").toMediaTypeOrNull())
            val hairstyleBody = Option.Opt.opt.hairstyle.style.toRequestBody(("text/plain").toMediaTypeOrNull())
            val prevHairstyleBody = Option.Opt.opt.prev_hairstyle.style.toRequestBody(("text/plain").toMediaTypeOrNull())
            val suitBody = "0".toRequestBody(("text/plain").toMediaTypeOrNull())

            // 로컬DB에 이미지 데이터 저장
            var data = Image(0, filePath)
            vm.insert(data)

            var options = hashMapOf("id" to idBody)
            options.put("sex", sexBody)
            options.put("face_shape", faceShapeBody)
            options.put("hairstyle", hairstyleBody)
            options.put("prev_hairstyle", prevHairstyleBody)
            options.put("suit", suitBody)

            // activate progress bar & disable the buttons
            activateProgressBar(true)

            // request resume photo to server in a coroutine scope
            runBlocking {
                ImageApi.instance.uploadResumePhoto(
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
                        if (response?.isSuccessful) {
                            val result = response.body()?.code
                            println(result)
                            when (result) {
                                1 -> {
                                    val decode_img = Base64.decode(response.body()?.data, Base64.DEFAULT)
                                    val bitmapResultImage = BitmapFactory.decodeByteArray(decode_img, 0, decode_img.size)

                                    activateProgressBar(false) // remove progress bar

                                    setFragmentResult("requestBitmapKey", bundleOf("bundleKey" to bitmapResultImage))
                                    Toast.makeText(context, "File Uploaded Successfully...", Toast.LENGTH_LONG).show()

                                    val transaction = activity?.supportFragmentManager!!.beginTransaction()
                                    transaction.replace(R.id.frameLayout, SynthesisConfirmFragment())
                                    transaction.commitAllowingStateLoss()
                                }
                                2 -> { // 얼굴 여러명 이상 감지
                                    var dialog = FaceDetectionFailureDialogFragment()
                                    dialog?.tv_dialog_message?.text = "2인 이상 감지되었습니다."
                                    dialog.isCancelable = false

                                    activity?.supportFragmentManager?.let { it ->
                                        dialog.show(it, "Face Detection Failure")
                                    }
                                    activateProgressBar(false)
                                }
                                3 -> { // 얼굴 인식 실패
                                    var dialog2 = FaceDetectionFailureDialogFragment()
                                    dialog2.tv_dialog_message.text = "얼굴 인식에 실패하였습니다."
                                    dialog2.isCancelable = false

                                    activity?.supportFragmentManager?.let { it ->
                                        dialog2.show(it, "Face Detection Failure")
                                    }
                                    activateProgressBar(false)
                                }
                            }
                        } else {
                            Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }
    }

    private fun activateProgressBar(isActive: Boolean) {
        when (isActive) {
            true -> {
                layoutProgressBar.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                progressText.visibility = View.VISIBLE
                btn_confirm.isEnabled = false
                btn_select_again.isEnabled = false
            }
            false -> {
                layoutProgressBar.visibility = View.GONE
                progressBar.visibility = View.GONE
                progressText.visibility = View.GONE
                btn_confirm.isEnabled = true
                btn_select_again.isEnabled = true
            }
        }
    }
}
