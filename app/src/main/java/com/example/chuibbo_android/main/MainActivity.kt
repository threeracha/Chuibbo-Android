package com.example.chuibbo_android.main

import GuidelineFragment
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.chuibbo_android.R
import com.example.chuibbo_android.home.HomeFragment
import com.example.chuibbo_android.mypage.MypageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener  { // PreferenceFragmentCompat.OnPreferenceStartFragmentCallback

//     var customFragmentFactory = CustomFragmentFactory("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        bottomNavigationView.background = null
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.home_item

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_previous)

        // TODO: 카메라 탭 클릭 시, 카메라 탭 활성화 및 다른 탭 비활성화
        camera_fab.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, GuidelineFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.home_item -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, HomeFragment())
                transaction.addToBackStack(null)
                transaction.commit()
                return true
            }
            R.id.mypage_item -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, MypageFragment())
                transaction.addToBackStack(null)
                transaction.commit()
                return true
            }
        }
        return false
    }

//    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat, pref: Preference): Boolean {
//        // Instantiate the new Fragment
//        val args = pref.extras
//        supportFragmentManager.fragmentFactory = customFragmentFactory
//        val fragment = supportFragmentManager.fragmentFactory.instantiate(
//            classLoader,
//            pref.fragment)
//        fragment.arguments = args
//        fragment.setTargetFragment(caller, 0)
//        // Replace the existing Fragment with the new Fragment
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.settings_fragment, fragment)
//            .addToBackStack(null)
//            .commit()
//        return true
//    }

    //뒤로가기 버튼을 뺏어올 리스너 등록

}

//class CustomFragmentFactory(private val dependency: Dependency) : FragmentFactory() {
//    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
//        if (className == MySettingsFragment::class.java.name) {
//            return MySettingsFragment(Dependency)
//        }
//        return super.instantiate(classLoader, className)
//    }
//}