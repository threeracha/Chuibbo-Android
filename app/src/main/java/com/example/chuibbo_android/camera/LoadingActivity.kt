package com.example.chuibbo_android.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.loading_activity.*

class LoadingActivity : AppCompatActivity() {
    // TODO: 2021/03/24 서버로부터의 합성된 이미지를 받아올 때 까지 로딩 화면 띄우기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_activity)
    }
}
