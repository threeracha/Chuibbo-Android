package com.example.chuibbo_android.preferences

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chuibbo_android.R

class MySettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferences_fragment)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_fragment, MySettingsFragment())
            .commit()
    }
}