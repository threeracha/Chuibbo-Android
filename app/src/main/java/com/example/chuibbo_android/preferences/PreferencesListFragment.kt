package com.example.chuibbo_android.preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import com.example.chuibbo_android.R
import com.example.chuibbo_android.login.LoginFragment

class PreferencesListFragment: PreferenceFragmentCompat() {

    lateinit var prefs: SharedPreferences
    lateinit var preference_logout: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_list, rootKey)
    }
}

//    override fun onCreateView(
//            inflater: LayoutInflater, container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//
//        addPreferencesFromResource(R.xml.preferences_list)
//
//        preference_logout = findPreference("Logout")
//
//        preference_logout.setOnPreferenceClickListener(Preference.OnPreferenceClickListener { preference ->
//            System.out.println("Logout")
//            true
//        })

//        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
//
//        prefs.registerOnSharedPreferenceChangeListener(prefListener);
//    }
//
//    override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
//        super.onPreferenceTreeClick(preferenceScreen, preference)
//
//        if (preference == preference_logout) System.out.println("Logout")
//            return true
//
//        return false
//    }
//}

// var prefListener = onPreferenceTreeClick(preferenceScreen: PreferenceScreen, preference:Preference ) {
//        sharedPreferences: SharedPreferences, key ->
//            if (key == "Logout") {
//                System.out.println(preference)
//                if (prefs.getBoolean("Logout", false)) {
//                    preference.setSummary("사용")
//                } else {
//                    preference.setSummary("사용안함")
//                }
//
//                //2뎁스 PreferenceScreen 내부에서 발생한 환경설정 내용을 2뎁스 PreferenceScreen에 적용하기 위한 소스
//                (preferenceScreen.rootAdapter as BaseAdapter).notifyDataSetChanged()
//            } else {
//                System.out.println("NO")
//            }
//        }
//        set(value) = TODO()




//class PreferencesListFragment: PreferenceFragmentCompat(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
//
//
//    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//        setPreferencesFromResource(R.xml.preferences_list, rootKey)
//    }
//
//    override fun onPreferenceStartFragment(
//        caller: PreferenceFragmentCompat?,
//        pref: Preference?
//    ): Boolean {
//        // Instantiate the new Fragment
//        print("클릭됨")
//        val args = pref?.extras
//        val fragment = activity?.supportFragmentManager!!.fragmentFactory.instantiate(
//                ClassLoader.getSystemClassLoader(),
//                pref!!.fragment)
//        fragment?.arguments = args
//        fragment?.setTargetFragment(caller, 0)
//        // Replace the existing Fragment with the new Fragment
//        if (fragment != null) {
//            activity?.supportFragmentManager?.beginTransaction()?.apply {
//                replace(R.id.frameLayout, fragment)
//                addToBackStack(null)
//            }?.commit()
//        }
//        return true
//    }
//}
