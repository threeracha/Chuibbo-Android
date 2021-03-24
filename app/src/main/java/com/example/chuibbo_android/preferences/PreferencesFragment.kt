package com.example.chuibbo_android.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.chuibbo_android.R

class PreferencesFragment: Fragment(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.preferences_fragment, container, false)
    }

    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat, pref: Preference): Boolean {
        // Instantiate the new Fragment
        val args = pref.extras
        val fragment = activity?.supportFragmentManager?.fragmentFactory?.instantiate(
                ClassLoader.getSystemClassLoader(),
                pref.fragment)
        fragment?.arguments = args
        fragment?.setTargetFragment(caller, 0)
        // Replace the existing Fragment with the new Fragment
        fragment?.let {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, it)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        return true
    }
}