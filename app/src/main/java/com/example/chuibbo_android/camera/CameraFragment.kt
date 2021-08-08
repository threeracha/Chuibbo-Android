package com.example.chuibbo_android.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.chuibbo_android.R
import com.example.chuibbo_android.camera.CameraOptionsFragment.Companion.newIntent
import com.example.chuibbo_android.utils.Common
import com.google.android.material.tabs.TabLayoutMediator
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.camera_fragment.*
import kotlinx.android.synthetic.main.main_activity.*

private const val NUM_PAGES = 2

class CameraFragment : Fragment() {
    private lateinit var optionsViewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar!!.title = "옵션 선택"

        optionsViewPager = optionViewPager
        val pagerAdapter2 = GenderSlidePagerAdapter(this)
        optionsViewPager.adapter = pagerAdapter2
        val common = Common(this, this.requireActivity())

        val tabLayout = tabLayout
        TabLayoutMediator(tabLayout, optionViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "여자"
                }
                1 -> {
                    tab.text = "남자"
                }
            }
        }.attach()

        setPermission() // 카메라 및 갤러리 접근 권한 요청

        gallery_capture_button.setOnClickListener {
            common.dispatchGalleryIntent()
        }
        camera_capture_button.setOnClickListener {
            common.dispatchTakePictureIntent()
        }
    }

    // 카메라 사용시 권한 설정 팝업 구현
    private fun setPermission() {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() { // 설정해 놓은 위험권한(카메라 접근 등)이 허용된 경우 이곳을 실행
                Toast.makeText(activity, "요청하신 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) { // 설정해 놓은 위험권한이 거부된 경우 이곳을 실행
                Toast.makeText(activity, "요청하신 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(activity)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
            .check()
    }

    private class GenderSlidePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment =
            newIntent(position)
    }

    companion object {
        var isFromGallery: Boolean = true
    }
}
