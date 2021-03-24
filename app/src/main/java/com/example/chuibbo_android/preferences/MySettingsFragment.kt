package com.example.chuibbo_android.preferences

import android.os.Bundle
import androidx.constraintlayout.solver.widgets.analyzer.Dependency
import androidx.preference.PreferenceFragmentCompat
import com.example.chuibbo_android.R

class MySettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_list, rootKey)
    }
}