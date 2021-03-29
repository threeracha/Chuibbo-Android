package com.example.chuibbo_android.preferences

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.preferences_withdrawal_fragment.view.*

class PreferencesWithdrawalFragment: Fragment() {
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.preferences_withdrawal_fragment, container, false)

        view.withdrawal_contents.movementMethod = ScrollingMovementMethod()

        view.withdrawal_checkbox.setOnCheckedChangeListener { buttonView, isChecked -> // 체크박스 선택시 버튼 활성화
            // TODO: 버튼 크기가 변경됨, 일관성 있게 만들기
            if (view.withdrawal_checkbox.isChecked) {
                view.withdrawal_button.isEnabled = true
                view.withdrawal_button.isClickable = true
                view.withdrawal_button.setBackgroundResource(R.drawable.button_shape)
                view.withdrawal_button.setTextColor(R.color.black) // TODO: textColor white로 바꾸기
            } else {
                view.withdrawal_button.isEnabled = false
                view.withdrawal_button.isClickable = false
                view.withdrawal_button.setBackgroundResource(R.drawable.button_shape_disabled)
                view.withdrawal_button.setTextColor(R.color.dark_gray)
            }
        }

        return view
    }
}