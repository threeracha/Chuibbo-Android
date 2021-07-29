package com.example.chuibbo_android.main

import GuidelineFragment
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.chuibbo_android.R
import com.example.chuibbo_android.home.HomeFragment
import com.example.chuibbo_android.mypage.MypageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener { // PreferenceFragmentCompat.OnPreferenceStartFragmentCallback

    override fun onStart() {
        super.onStart()
        // Timber.i("onStart Called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        bottomNavigationView.background = null
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.home_item

        //setSupportActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.home_item -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, HomeFragment())
                transaction.addToBackStack(null)
                transaction.commit()
                return true
            }
            R.id.calendar_item -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, MypageFragment())
                transaction.addToBackStack(null)
                transaction.commit()
                return true
            }
            R.id.camera_item -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, GuidelineFragment())
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
    // TODO: 뒤로가기 버튼을 뺏어올 리스너 등록
}
