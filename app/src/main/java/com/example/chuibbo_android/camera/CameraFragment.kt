package com.example.chuibbo_android.camera

import com.example.chuibbo_android.camera.CameraOptionsFragment.Companion.newIntent
import GuidelineFragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.chuibbo_android.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.camera_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val NUM_PAGES = 2

class CameraFragment : Fragment() {
    private lateinit var currentPhotoPath: String
    private lateinit var optionsViewPager : ViewPager2

    private val requestCameraActivity = registerForActivityResult(
        StartActivityForResult()
    ) { activityResult ->
        setFragmentResult("requestKey", bundleOf("bundleKey" to currentPhotoPath))
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.frameLayout, ConfirmFragment())
        clearBackStack()
        transaction.commit()
    }

    private val requestGalleryActivity = registerForActivityResult(
        StartActivityForResult()
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
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar!!.setTitle("옵션 선택")

        optionsViewPager = optionViewPager
        val pagerAdapter2 = GenderSlidePagerAdapter(this)
        optionsViewPager.adapter = pagerAdapter2

        val tabLayout = tabLayout
        TabLayoutMediator(tabLayout,  optionViewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "여자"
                }
                1 -> {
                    tab.text = "남자"
                }
            }
        }.attach()

        gallery_capture_button.setOnClickListener { galleryAddPic() }
        camera_capture_button.setOnClickListener { dispatchTakePictureIntent() }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity?.packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.d("test", "error: $ex")
                    null
                }
                var photoURI : Uri? = null
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

    private fun galleryAddPic() {
        Intent(Intent.ACTION_PICK).also { mediaScanIntent ->
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                Log.d("test", "error: $ex")
                null
            }
            var photoURI : Uri? = null
            photoFile?.also {
                activity?.also {
                    photoURI = FileProvider.getUriForFile(it, "com.example.chuibbo_android.camera", photoFile)
                }
                mediaScanIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                requestGalleryActivity.launch(mediaScanIntent)
            }
        }
    }

    // 사진 파일을 만드는 메소드
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        return image
    }

    /**
     * A pager adapter that represents 4 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class GenderSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment =
            newIntent(position)
    }
}