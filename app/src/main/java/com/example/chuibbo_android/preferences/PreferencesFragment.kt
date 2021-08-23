package com.example.chuibbo_android.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.faq.PreferencesFaqFragment
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.preferences_fragment.view.*

class PreferencesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar_title!!.text = "설정"
        activity?.back_button!!.visibility = View.VISIBLE

        return inflater.inflate(R.layout.preferences_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.preferences_user_info_modification_button?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesUserInfoModificationFragment())
                addToBackStack(null)
            }?.commit()
        }

        view.preferences_notice_button?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesNoticeFragment())
                addToBackStack(null)
            }?.commit()
        }

        view.preferences_faq_button?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesFaqFragment())
                addToBackStack(null)
            }?.commit()
        }

        view.preferences_inquiry_button?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesInquiry())
                addToBackStack(null)
            }?.commit()
        }

        var deleteCacheDialog: PreferencesDeleteCacheDialogFragment = PreferencesDeleteCacheDialogFragment()
        deleteCacheDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기
        view.preferences_delete_cache_button?.setOnClickListener {
            activity?.supportFragmentManager?.let { it -> deleteCacheDialog.show(it, "Delete Cache") }
        }

        var logoutDialog: PreferencesLogoutDialogFragment = PreferencesLogoutDialogFragment()
        logoutDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기
        view.preferences_logout_button?.setOnClickListener {
            activity?.supportFragmentManager?.let { it -> logoutDialog.show(it, "Logout") }
        }

        view.preferences_withdrawal_button?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesWithdrawalFragment())
                addToBackStack(null)
            }?.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
    }
}
