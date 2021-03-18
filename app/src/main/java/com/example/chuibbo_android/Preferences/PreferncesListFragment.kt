package com.example.chuibbo_android.settings

import android.os.Bundle
import android.preference.PreferenceFragment
import com.example.chuibbo_android.R

class SettingsListFragment: PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.settings_list)
    }
}