package com.example.chuibbo_android.Preferences

import android.os.Bundle
import android.preference.PreferenceFragment
import com.example.chuibbo_android.R

class PreferencesListFragment: PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.preferences_list)
    }
}