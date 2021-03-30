package com.example.chuibbo_android.camera

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import com.example.chuibbo_android.R
import com.example.chuibbo_android.image.Image
import com.example.chuibbo_android.image.ImageViewModel
import kotlinx.android.synthetic.main.confirm_fragment.*
import kotlinx.android.synthetic.main.main_activity.*

class ConfirmFragment : Fragment() {
    lateinit var filePath : String
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

        var result : String
        var resultUri : Uri

        vm = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ImageViewModel::class.java)

        setFragmentResultListener("requestKey") { key, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            if (bundle.getString("bundleKey") != null) { // camera
                filePath = bundle.getString("bundleKey")
                result = bundle.getString("bundleKey")
                BitmapFactory.decodeFile(result)?.also { bitmap ->
                    img_preview.setImageBitmap(bitmap)
                }

            } else if (bundle.getParcelable<Uri>("bundleKeyUri") != null) { // gallery
                val uriPathHelper = URIPathHelper()
                filePath = uriPathHelper.getPath(requireContext(), bundle.getParcelable<Uri>("bundleKeyUri")).toString()
                resultUri = bundle.getParcelable("bundleKeyUri")
                img_preview.setImageURI(resultUri)
            }
        }
        return inflater.inflate(R.layout.confirm_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar!!.setTitle("사진 선택")
        // FIXME: 2021/03/25 여기서 뒤로가기 버튼 누르면 앱이 종료됨

        btn_cancel.setOnClickListener {
            // TODO: 2021/03/26 스택 이름을 나눠서 지정하여 여기서 꺼내기 
            galleryAddPic()
        }
        btn_confirm.setOnClickListener {
            // TODO: 2021/03/24 이미지 서버로 보내고 로딩 페이지 띄우기. 서버로부터 이미지 받으면 SysthesisFragment 띄우기
            // room 에 이미지 데이터 저장
            var data = Image(0, filePath)
            vm.insert(data)
            Toast.makeText(context, "Successfully saved", Toast.LENGTH_LONG).show()
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.frameLayout, SynthesisConfirmFragment())
            transaction.commit()
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