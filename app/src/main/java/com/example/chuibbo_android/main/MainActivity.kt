package com.example.chuibbo_android.main

import GuidelineFragment
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.chuibbo_android.R
import com.example.chuibbo_android.calendar.CalendarFragment
import com.example.chuibbo_android.home.HomeFragment
import com.example.chuibbo_android.login.LoginFragment
import com.example.chuibbo_android.mypage.MypageFragment
import com.example.chuibbo_android.auth.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*

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
        back_button.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // TODO: 클릭 한 번 시, 기존에 수행하던 fragment 제공
        // TODO: 클릭 두 번 시, 새 fragment 제공

        val transaction = supportFragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.home_item -> {
                transaction.replace(R.id.frameLayout, HomeFragment())
                transaction.addToBackStack("home")
            }
            R.id.calendar_item -> {
                transaction.replace(R.id.frameLayout, CalendarFragment())
                transaction.addToBackStack("calendar")
            }
            R.id.camera_item -> {
                transaction.replace(R.id.frameLayout, GuidelineFragment())
                transaction.addToBackStack("camera")
            }
            R.id.mypage_item -> {
                // TODO: 토큰이 유효한지 서버로 확인
                val user_info = sessionManager.fetchUserInfo()

                if (user_info != "") { // 로그인 유효시,
                    transaction.replace(R.id.frameLayout, MypageFragment())
                    transaction.addToBackStack("mypage")

                } else { // 로그인 토큰이 없을 때,
                    transaction.replace(R.id.frameLayout, LoginFragment())
                    transaction.addToBackStack("login")
                }
            }
        }

        transaction.commit()
        transaction.isAddToBackStackAllowed
        return true
    }

    // TODO: 뒤로가기 버튼을 뺏어올 리스너 등록
}
