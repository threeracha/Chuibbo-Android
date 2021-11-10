package com.example.chuibbo_android.preferences

import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.preferences_withdrawal_fragment.view.*

class PreferencesWithdrawalFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.preferences_withdrawal_fragment, container, false)

        activity?.toolbar_title!!.text = "회원탈퇴"
        activity?.back_button!!.visibility = View.VISIBLE

        view.withdrawal_contents.movementMethod = ScrollingMovementMethod()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.withdrawal_checkbox.setOnCheckedChangeListener { buttonView, isChecked -> // 체크박스 선택시 버튼 활성화
            if (view.withdrawal_checkbox.isChecked) {
                view.withdrawal_button.isEnabled = true
                view.withdrawal_button.isClickable = true
                view.withdrawal_button.setBackgroundResource(R.drawable.button_shape)
                view.withdrawal_button.setTextColor(Color.WHITE)
            } else {
                view.withdrawal_button.isEnabled = false
                view.withdrawal_button.isClickable = false
                view.withdrawal_button.setBackgroundResource(R.drawable.button_shape_disabled)
                view.withdrawal_button.setTextColor(Color.DKGRAY)
            }
        }

        var withdrawalDialog: PreferencesWithdrawalDialogFragment = PreferencesWithdrawalDialogFragment()
        withdrawalDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기
        view.withdrawal_button.setOnClickListener{
            activity?.supportFragmentManager?.let { it -> withdrawalDialog.show(it, "Withdrawal") }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
    }
}
