package com.example.chuibbo_android.signup

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.trimmedLength
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.main_activity.*
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
        activity?.toolbar!!.title = "계정 생성"

        // TODO: 회원가입 이메일과 비밀번호 정책 세우기
        email_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) { // 이메일 형식이 올바르지 않은 경우
                    email_text_error.visibility = View.VISIBLE
                } else {
                    email_text_error.visibility = View.INVISIBLE
                }
            }
        })

        password_check_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(password_edit_text.text.toString() != password_check_edit_text.text.toString()) { // 비밀번호가 같지 않은 경우
                    password_check_text_error.visibility = View.VISIBLE
                } else {
                    password_check_text_error.visibility = View.INVISIBLE
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })

        // Listener 등록
        nickname_edit_text.addTextChangedListener(EditTextWatcher()) // EditText 변경 시 버튼 활성화
        email_edit_text.addTextChangedListener(EditTextWatcher())
        password_edit_text.addTextChangedListener(EditTextWatcher())
        password_check_edit_text.addTextChangedListener(EditTextWatcher())
        agree_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->  // 체크박스 선택 시 버튼 활성화
            checkEditTextAndCheckBox()
        }
    }

    // EditText에 등록할 Listener
    inner class EditTextWatcher: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int,
                                   before: Int, count: Int) {
            checkEditTextAndCheckBox()
        }
    }

    // EditText에 내용이 들어있고, CheckBox에 체크 되어있다면 계정생성하기 버튼 활성화
    fun checkEditTextAndCheckBox(): Unit {
        if (agree_checkbox.isChecked) {
            if (nickname_edit_text.text.toString().trimmedLength() != 0 && email_edit_text.toString().trimmedLength() != 0
                    && password_edit_text.text.toString().trimmedLength() != 0 && password_check_edit_text.text.toString().trimmedLength() != 0) {
                create_account_button.isEnabled = true
                create_account_button.isClickable = true
                create_account_button.setBackgroundResource(R.drawable.button_shape)
                create_account_button.setTextColor(Color.WHITE)

                return
            }
        }

        create_account_button.isEnabled = false
        create_account_button.isClickable = false
        create_account_button.setBackgroundResource(R.drawable.button_shape_disabled)
        create_account_button.setTextColor(Color.DKGRAY)

        return
    }
}