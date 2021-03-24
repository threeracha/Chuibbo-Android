package com.example.chuibbo_android.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.signup_fragment.*


class SignupFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) { // 이메일 형식이 올바르지 않은 경우
                    email_edit_text.setBackgroundResource(R.drawable.input_text_red) // 적색 테두리 적용
                    email_text_error.visibility = View.VISIBLE
                } else {
                    email_edit_text.setBackgroundResource(R.drawable.main_button) // 기본 테두리 적용
                    email_text_error.visibility = View.INVISIBLE
                }
            }
        })

        password_check_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(password_edit_text.text.toString() != password_check_edit_text.text.toString()) { // 비밀번호가 같지 않은 경우
                    password_check_edit_text.setBackgroundResource(R.drawable.input_text_red) // 적색 테두리 적용
                    password_check_text_error.visibility = View.VISIBLE
                } else {
                    password_check_edit_text.setBackgroundResource(R.drawable.main_button) // 기본 테두리 적용
                    password_check_text_error.visibility = View.INVISIBLE
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }
}