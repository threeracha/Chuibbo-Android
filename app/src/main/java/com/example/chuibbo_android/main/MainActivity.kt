package com.example.chuibbo_android.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.chuibbo_android.R
import com.example.chuibbo_android.camera.CameraFragment
import com.example.chuibbo_android.home.HomeFragment
import com.example.chuibbo_android.login.LoginFragment
import com.example.chuibbo_android.mypage.MypageFragment
import com.example.chuibbo_android.signup.SignupFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private final var FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0

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
                transaction.replace(R.id.frameLayout, CameraFragment())
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
                transaction.replace(R.id.frameLayout, LoginFragment()) // 원래는 MypageFragment()
                transaction.addToBackStack(null)
                transaction.commit()
                return true
            }
        }
        return false
    }

    //뒤로가기 버튼을 뺏어올 리스너 등록

}