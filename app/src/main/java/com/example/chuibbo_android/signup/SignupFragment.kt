package com.example.chuibbo_android.signup

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.UserApi
import com.example.chuibbo_android.api.response.SpringResponse
import com.example.chuibbo_android.login.LoginFragment
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.signup_fragment.*
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : Fragment() {
    var nicknameCheck = false
    var emailCheck = false
    var passwordCheck1 = false
    var passwordCheck2 = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar_title!!.text = "계정 생성"
        activity?.back_button!!.visibility = View.VISIBLE

        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var signupDialog: SignupDialogFragment = SignupDialogFragment()
        signupDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기

        // 닉네임 작성 확인 (작성 확인시, 닉네임 중복확인 버튼 활성화)
        // TODO: 닉네임 10자 이내의 영문, 숫자, 한글만 허용한다.
        nickname_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                nicknameCheck = false

                if (s.toString().isNullOrBlank()) {
                    nickname_check.isEnabled = false
                    nickname_check.backgroundTintList = context?.let { ContextCompat.getColor(it, R.color.gray) }?.let { ColorStateList.valueOf(it) }
                } else {
                    nickname_check.isEnabled = true
                    nickname_check.backgroundTintList = context?.let { ContextCompat.getColor(it, R.color.main_blue) }?.let { ColorStateList.valueOf(it) }
                }

                checkEditTextAndCheckBox()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        nickname_check.setOnClickListener {
            val nickname = nickname_edit_text.text.toString()

            runBlocking {
                UserApi.instance.checkNickname(
                    nickname = nickname
                ).enqueue(object : Callback<SpringResponse<String>> {
                    override fun onFailure(call: Call<SpringResponse<String>>, t: Throwable) {
                        Log.d("retrofit fail", t.message)
                    }

                    override fun onResponse(
                        call: Call<SpringResponse<String>>,
                        response: Response<SpringResponse<String>>
                    ) {
                        if (response.isSuccessful) {
                            when (response.body()?.result_code) {
                                "DATA OK" -> {
                                    activity?.supportFragmentManager?.let { it1 -> signupDialog.show(it1, "Nickname Check OK") }

                                    // 성공시
                                    nicknameCheck = true
                                    checkEditTextAndCheckBox()
                                }
                                "ERROR" -> {
                                    activity?.supportFragmentManager?.let { it1 -> signupDialog.show(it1, "Nickname Check ERROR") }

                                    // 실패시
                                    nicknameCheck = false
                                    checkEditTextAndCheckBox()
                                }
                            }
                        }
                    }
                })
            }
        }

        // 이메일 형식 확인 (형식 확인시, 이메일 중복확인 버튼 활성화)
        email_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                emailCheck = false

                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    email_text_error.visibility = View.VISIBLE
                    email_check.isEnabled = false
                    email_check.backgroundTintList = context?.let { ContextCompat.getColor(it, R.color.gray) }?.let { ColorStateList.valueOf(it) }
                } else {
                    email_text_error.visibility = View.INVISIBLE
                    email_check.isEnabled = true
                    email_check.backgroundTintList = context?.let { ContextCompat.getColor(it, R.color.main_blue) }?.let { ColorStateList.valueOf(it) }
                }

                checkEditTextAndCheckBox()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        email_check.setOnClickListener {
            val email = email_edit_text.text.toString()

            runBlocking {
                UserApi.instance.checkEmail(
                    email = email
                ).enqueue(object : Callback<SpringResponse<String>> {
                    override fun onFailure(call: Call<SpringResponse<String>>, t: Throwable) {
                        Log.d("retrofit fail", t.message)
                    }

                    override fun onResponse(
                        call: Call<SpringResponse<String>>,
                        response: Response<SpringResponse<String>>
                    ) {
                        if (response.isSuccessful) {
                            when (response.body()?.result_code) {
                                "DATA OK" -> {
                                    activity?.supportFragmentManager?.let { it1 -> signupDialog.show(it1, "Email Check OK") }

                                    // 성공시
                                    emailCheck = true
                                    checkEditTextAndCheckBox()
                                }
                                "ERROR" -> {
                                    activity?.supportFragmentManager?.let { it1 -> signupDialog.show(it1, "Email Check ERROR") }

                                    // 실패시
                                    emailCheck = false
                                    checkEditTextAndCheckBox()
                                }
                            }
                        }
                    }
                })
            }
        }

        // 비밀번호 제약 확인
        password_edit_text.addTextChangedListener(CheckPassword())

        // 비밀번호와 비밀번호 체크 동일한지 확인
        password_edit_text.addTextChangedListener(CheckPasswordMatch())
        password_check_edit_text.addTextChangedListener(CheckPasswordMatch())

        // 모든 항목 작성 후, 중복확인하고, 체크박스 체크 시 계정생성 버튼 활성화
        agree_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            checkEditTextAndCheckBox()
        }

        // 계정생성 버튼 클릭시, 서버에 회원가입하기
        create_account_button.setOnClickListener{
            val nickname = nickname_edit_text.text.toString()
            val email = email_edit_text.text.toString()
            val password = password_edit_text.text.toString()

            var signupInfo = hashMapOf("nickname" to nickname)
            signupInfo["email"] = email
            signupInfo["password"] = password

            runBlocking {
                UserApi.instance.signup(
                    data = signupInfo
                ).enqueue(object : Callback<SpringResponse<String>> {
                    override fun onFailure(call: Call<SpringResponse<String>>, t: Throwable) {
                        Log.d("retrofit fail", t.message)
                    }

                    override fun onResponse(
                        call: Call<SpringResponse<String>>,
                        response: Response<SpringResponse<String>>
                    ) {
                        if (response.isSuccessful) {
                            when (response.body()?.result_code) {
                                "OK" -> {
                                    // TODO: 회원가입 성공! 로그인하러가기

                                    val transaction = activity?.supportFragmentManager!!.beginTransaction()
                                    transaction.replace(R.id.frameLayout, LoginFragment())
                                }
                                "ERROR" -> {
                                    activity?.supportFragmentManager?.let { it1 -> signupDialog.show(it1, "Signup Failure") }
                                }
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
    }

    inner class CheckPassword : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            checkEditTextAndCheckBox()
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            passwordCheck1 = false

            if (!isPasswordFormat(s.toString()))
                password_text_error.visibility = View.VISIBLE
            else {
                passwordCheck1 = true
                password_text_error.visibility = View.INVISIBLE
            }
        }

        private fun isPasswordFormat(password: String): Boolean {
            return password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[?!@#\$%^&*]).{8,15}\$".toRegex())
        }
    }

    inner class CheckPasswordMatch : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            checkEditTextAndCheckBox()
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            passwordCheck2 = false

            if (password_check_edit_text.text.toString().isNullOrBlank())
                password_check_text_error.visibility = View.INVISIBLE
            else if (password_check_edit_text.text.toString() != password_edit_text.text.toString())
                password_check_text_error.visibility = View.VISIBLE
            else {
                passwordCheck2 = true
                password_check_text_error.visibility = View.INVISIBLE
            }
        }
    }

    fun checkEditTextAndCheckBox() {
        if (agree_checkbox.isChecked) {
            if (nicknameCheck && emailCheck && passwordCheck1 && passwordCheck2) {
                create_account_button.isEnabled = true
                create_account_button.setBackgroundResource(R.drawable.button_shape)
                create_account_button.setTextColor(Color.WHITE)

                return
            }
        }

        create_account_button.isEnabled = false
        create_account_button.setBackgroundResource(R.drawable.button_shape_disabled)
        create_account_button.setTextColor(Color.DKGRAY)

        return
    }
}
