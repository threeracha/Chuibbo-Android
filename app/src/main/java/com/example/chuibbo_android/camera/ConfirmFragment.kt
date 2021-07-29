package com.example.chuibbo_android.camera

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.ImageApi
import com.example.chuibbo_android.image.Image
import com.example.chuibbo_android.image.ImageViewModel
import kotlinx.android.synthetic.main.confirm_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class ConfirmFragment : Fragment() {
    lateinit var filePath: String
    lateinit var vm: ImageViewModel

    private val requestGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        setFragmentResult("requestKey", bundleOf("bundleKeyUri" to activityResult.data!!.data))
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.frameLayout, ConfirmFragment())
        clearBackStack()
        transaction.commit()
    }

    private val requestSynthesisConfirmFragment = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        setFragmentResult("requestKey", bundleOf("bundleKeyUri" to activityResult.data!!.data))
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.frameLayout, SynthesisConfirmFragment())
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
        activity?.toolbar_title!!.text = "사진 선택"

        // FIXME: 2021/03/25 여기서 뒤로가기 버튼 누르면 앱이 종료됨

        btn_cancel.setOnClickListener {
            // TODO: 2021/03/26 스택 이름을 나눠서 지정하여 여기서 꺼내기 
            galleryAddPic()
        }
        btn_confirm.setOnClickListener {
            // TODO: 2021/03/24 이미지 서버로 보내고 로딩 페이지 띄우기. 서버로부터 이미지 받으면 SysthesisFragment 띄우기
            var fileBody: RequestBody
            var filePart: MultipartBody.Part
            var okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
            var retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            var server = retrofit.create(ImageApi::class.java)

            // room 에 이미지 데이터 저장
            var data = Image(0, filePath)
            vm.insert(data)
            // Toast.makeText(context, "Successfully saved", Toast.LENGTH_LONG).show()

            fileBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), File(filePath))
            filePart = createFormData("photo", "photo.jpg", fileBody)

            // activate progress bar & disable the buttons
            layoutProgressBar.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            progressText.visibility = View.VISIBLE
            btn_confirm.isEnabled = false
            btn_cancel.isEnabled = false

            // request resume photo to server in a coroutine scope
            runBlocking {
                server.uploadResumePhoto(filePart).enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("retrofit fail", t.message)
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response?.isSuccessful) {
                            Toast.makeText(context, "File Uploaded Successfully...", Toast.LENGTH_LONG).show()

                            val file = response.body()?.byteStream()
                            val bitmapResultImage = BitmapFactory.decodeStream(file)

                            // remove progress bar
                            layoutProgressBar.visibility = View.GONE
                            progressBar.visibility = View.GONE
                            progressText.visibility = View.GONE

                            setFragmentResult("requestBitmapKey", bundleOf("bundleKey" to bitmapResultImage))
                            val transaction = activity?.supportFragmentManager!!.beginTransaction()
                            transaction.replace(R.id.frameLayout, SynthesisConfirmFragment())
                            transaction.commitAllowingStateLoss()
                        } else {
                            Toast.makeText(context, "Some error occurred...", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_PICK).also { mediaScanIntent ->
            mediaScanIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            requestGalleryActivity.launch(mediaScanIntent)
        }
    }

    // TODO: 이미지 resize 할 size 정하기 & 함수 적용
    // ImageView에 사진을 넣는 메소드
    private fun setPic(photoPath: String) {
        var img: ImageView = img_preview

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
