package com.example.chuibbo_android.main

import GuidelineFragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.UserApi
import com.example.chuibbo_android.api.response.SpringResponse
import com.example.chuibbo_android.api.response.User
import com.example.chuibbo_android.calendar.CalendarFragment
import com.example.chuibbo_android.home.HomeFragment
import com.example.chuibbo_android.login.LoginFragment
import com.example.chuibbo_android.mypage.MypageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                val preferences = getSharedPreferences(
                    "MY_APP",
                    Context.MODE_PRIVATE
                )
                val access_token = preferences?.getString("access_token", "")

                // TODO: 로그인 되었을 때, 마이페이지 전환 전, 로그인 살짝 뜨는 것 제거
                if (access_token != "") { // 로그인 토큰이 있을 때,
                    runBlocking {
                        UserApi.instance.userInfo(
                            access_token!!
                        ).enqueue(object : Callback<SpringResponse<User>> {
                            override fun onFailure(call: Call<SpringResponse<User>>, t: Throwable) {
                                Log.d("retrofit fail", t.message)
                            }

                            override fun onResponse(
                                call: Call<SpringResponse<User>>,
                                response: Response<SpringResponse<User>>
                            ) {
                                if (response.isSuccessful) {
                                    when (response.body()?.result_code) {
                                        "DATA OK" -> {
                                            val transaction = supportFragmentManager.beginTransaction()
                                            transaction.replace(R.id.frameLayout, MypageFragment())
                                            transaction.addToBackStack(null)
                                            transaction.commit()
                                            return
                                        }
                                        "ERROR" -> {
                                            val transaction = supportFragmentManager.beginTransaction()
                                            transaction.replace(R.id.frameLayout, LoginFragment())
                                            transaction.addToBackStack(null)
                                            transaction.commit()
                                            return
                                        }
                                    }
                                }
                            }
                        })
                    }
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
