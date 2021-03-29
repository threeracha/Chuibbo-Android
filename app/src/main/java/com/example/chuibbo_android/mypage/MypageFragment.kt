package com.example.chuibbo_android.mypage

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.background.BackgroundSynthesisFragment
import com.example.chuibbo_android.login.LoginFragment
import com.example.chuibbo_android.preferences.MySettingsActivity
import kotlinx.android.synthetic.main.mypage_fragment.view.*

class MypageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.mypage_fragment, container, false)

        view.login_button.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, LoginFragment())
                addToBackStack(null)
            }?.commit()
        }

        view.background_synthesis_image_button.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, BackgroundSynthesisFragment())
                addToBackStack(null)
            }?.commit()
        }

//        view.settings_button.setOnClickListener {
//            activity?.supportFragmentManager?.beginTransaction()?.apply {
//                replace(R.id.frameLayout, PreferencesFragment())
//                addToBackStack(null)
//            }?.commit()
//        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.settings_button.setOnClickListener {
            val intent = Intent(activity, MySettingsActivity::class.java)
            startActivity(intent)
        }
    }
}