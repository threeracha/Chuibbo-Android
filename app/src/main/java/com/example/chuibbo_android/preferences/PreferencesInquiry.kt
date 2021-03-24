package com.example.chuibbo_android.preferences

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.trimmedLength
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.preferences_inquiry_fragment.*

class PreferencesInquiry: Fragment() {
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var v: View = inflater.inflate(R.layout.preferences_withdrawal_fragment, container, false)

        inquiry_contents.addTextChangedListener(object: TextWatcher { // 문의하기 내용 입력시 버튼 활성화
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                // TODO: 버튼 크기가 변경됨, 일관성 있게 만들기
                if (inquiry_contents.text.toString().trimmedLength() != 0) {
                    inquiry_button.isEnabled = true
                    inquiry_button.isClickable = true
                    inquiry_button.setBackgroundResource(R.drawable.button_shape)
                    inquiry_button.setTextColor(R.color.black) // TODO: textColor white로 바꾸기
                } else {
                    inquiry_button.isEnabled = false
                    inquiry_button.isClickable = false
                    inquiry_button.setBackgroundResource(R.drawable.button_shape_disabled)
                    inquiry_button.setTextColor(R.color.dark_gray)
                }
            }
        })

        return v
    }
}