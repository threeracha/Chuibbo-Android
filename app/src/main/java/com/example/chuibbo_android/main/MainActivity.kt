package com.example.chuibbo_android.main

import GuidelineFragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.UserApi
import com.example.chuibbo_android.api.response.SpringResponse
import com.example.chuibbo_android.api.response.User
import com.example.chuibbo_android.calendar.CalendarFragment
import com.example.chuibbo_android.home.HomeFragment
import com.example.chuibbo_android.login.LoginFragment
import com.example.chuibbo_android.mypage.MypageFragment
import com.example.chuibbo_android.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener { // PreferenceFragmentCompat.OnPreferenceStartFragmentCallback

    private lateinit var sessionManager: SessionManager
    private lateinit var context: Context

    override fun onStart() {
        super.onStart()
        // Timber.i("onStart Called")

        sessionManager = SessionManager(this)
        context = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        bottomNavigationView.background = null
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.home_item

        // setSupportActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.home_item -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, HomeFragment())
                transaction.addToBackStack(null)
                transaction.commit()
                return true
            }
            R.id.calendar_item -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, CalendarFragment())
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
                val user_info = sessionManager.fetchUserInfo()

                if (user_info != "") { // 로그인 유효시,
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.frameLayout, MypageFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                    return true
                }
                // 로그인 토큰이 없을 때,
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, LoginFragment())
                transaction.addToBackStack(null)
                transaction.commit()
                return true
            }
        }
        return false
    }
    // TODO: 뒤로가기 버튼을 뺏어올 리스너 등록
}
