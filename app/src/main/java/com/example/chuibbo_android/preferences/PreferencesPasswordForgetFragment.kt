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
import kotlinx.android.synthetic.main.preferences_password_forget_fragment.*

class PreferencesPasswordForgetFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.preferences_password_forget_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_text.addTextChangedListener(EditTextWatcher())

        certification_code.addTextChangedListener(EditTextWatcher2())

        continue_button.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesPasswordModificationFragment())
                addToBackStack(null)
            }?.commit()
        }
    }

    @SuppressLint("ResourceAsColor")
    inner class EditTextWatcher: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int,
                                   before: Int, count: Int) {
            if (email_text.toString().trimmedLength() != 0) {
                certification_button.isEnabled = true
                certification_button.isClickable = true
                certification_button.setBackgroundResource(R.drawable.button_shape)
                certification_button.setTextColor(R.color.black) // TODO: textColor white로 바꾸기

                return
            } else {
                // TODO: 버튼 크기가 변경됨, 일관성 있게 만들기
                certification_button.isEnabled = false
                certification_button.isClickable = false
                certification_button.setBackgroundResource(R.drawable.button_shape_disabled)
                certification_button.setTextColor(R.color.dark_gray)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    inner class EditTextWatcher2: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int,
                                   before: Int, count: Int) {
            if (email_text.toString().trimmedLength() != 0) {
                continue_button.isEnabled = true
                continue_button.isClickable = true
                continue_button.setBackgroundResource(R.drawable.button_shape)
                continue_button.setTextColor(R.color.black) // TODO: textColor white로 바꾸기

                return
            } else {
                // TODO: 버튼 크기가 변경됨, 일관성 있게 만들기
                continue_button.isEnabled = false
                continue_button.isClickable = false
                continue_button.setBackgroundResource(R.drawable.button_shape_disabled)
                continue_button.setTextColor(R.color.dark_gray)
            }
        }
    }
}