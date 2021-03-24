package com.example.chuibbo_android.preferences

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.preferences_withdrawal_fragment.*

class PreferencesWithdrawalFragment: Fragment() {
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v: View = inflater.inflate(R.layout.preferences_withdrawal_fragment, container, false)

        withdrawal_contents.setMovementMethod(ScrollingMovementMethod())

        withdrawal_checkbox.setOnCheckedChangeListener { buttonView, isChecked -> // 체크박스 선택시 버튼 활성화
            // TODO: 버튼 크기가 변경됨, 일관성 있게 만들기
            if (withdrawal_checkbox.isChecked) {
                withdrawal_button.isEnabled = true
                withdrawal_button.isClickable = true
                withdrawal_button.setBackgroundResource(R.drawable.button_shape)
                withdrawal_button.setTextColor(R.color.black) // TODO: textColor white로 바꾸기
            } else {
                withdrawal_button.isEnabled = false
                withdrawal_button.isClickable = false
                withdrawal_button.setBackgroundResource(R.drawable.button_shape_disabled)
                withdrawal_button.setTextColor(R.color.dark_gray)
            }
        }

        return v
    }
}