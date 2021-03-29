package com.example.chuibbo_android.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.preferences_fragment.view.*

class PreferencesFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.preferences_fragment, container, false)

        view.preferences_user_info_modification_button?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesUserInfoModificationFragmentFragment())
                addToBackStack(null)
            }?.commit()
        }

//        view.preferences_notice_button?.setOnClickListener {
//            activity?.supportFragmentManager?.beginTransaction()?.apply {
//                replace(R.id.frameLayout, PreferencesNoticeFragment())
//                addToBackStack(null)
//            }?.commit()
//        }
//
//        view.preferences_faq_button?.setOnClickListener {
//            activity?.supportFragmentManager?.beginTransaction()?.apply {
//                replace(R.id.frameLayout, PreferencesFaqFragment())
//                addToBackStack(null)
//            }?.commit()
//        }

        view.preferences_inquiry_button?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesInquiry())
                addToBackStack(null)
            }?.commit()
        }

        view.preferences_withdrawal_button?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesWithdrawalFragment())
                addToBackStack(null)
            }?.commit()
        }

        return view
    }
}