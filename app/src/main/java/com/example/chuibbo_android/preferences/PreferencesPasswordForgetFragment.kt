package com.example.chuibbo_android.preferences

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.trimmedLength
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.UserApi
import com.example.chuibbo_android.api.request.LoginRequest
import com.example.chuibbo_android.api.response.SpringResponse
import com.example.chuibbo_android.api.response.UserResponse
import com.example.chuibbo_android.utils.SessionManager
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.preferences_password_forget_fragment.*
import kotlinx.android.synthetic.main.preferences_password_forget_fragment.email_text
import kotlinx.android.synthetic.main.preferences_password_forget_fragment.email_text_error
import kotlinx.android.synthetic.main.preferences_password_forget_fragment.password_text
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreferencesPasswordForgetFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar_title!!.text = "비밀번호 재설정"
        activity?.back_button!!.visibility = View.VISIBLE

        return inflater.inflate(R.layout.preferences_password_forget_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        email_text.addTextChangedListener(EditTextWatcher())

        var preferencesDialog: PreferencesDialogFragment = PreferencesDialogFragment()
        preferencesDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기

        send_email_button.setOnClickListener {
            val emailString = email_text.text.toString()
            val email = email_text.text.toString().toRequestBody()

            // 기존에 이메일이 있는지 확인
            runBlocking {
                UserApi.instance(requireContext()).checkEmail(
                    email = emailString
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
                                    activity?.supportFragmentManager?.let { it1 -> preferencesDialog.show(it1, "Check Email") }
                                }
                                "ERROR" -> {
                                    runBlocking {
                                        UserApi.instance(requireContext()).findPassword(
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
                                                        "OK" -> {
                                                            activity?.supportFragmentManager?.let { it1 -> preferencesDialog.show(it1, "Send Email") }
                                                        }
                                                        "ERROR" -> {
                                                            activity?.supportFragmentManager?.let { it1 -> preferencesDialog.show(it1, "Send Email Failure") }

                                                        }
                                                    }
                                                }
                                            }
                                        })
                                    }
                                }
                            }
                        }
                    }
                })
            }
        }

        password_text.addTextChangedListener(EditTextWatcher2())

        continue_button.setOnClickListener {
            val email = email_text.text.toString()
            val password = password_text.text.toString()

            val loginRequest = LoginRequest(email, password)

            runBlocking {
                UserApi.instance(requireContext()).login(
                    loginRequest
                ).enqueue(object : Callback<UserResponse> {
                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("retrofit fail", t.message)
                    }

                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {
//                            when (response.body()?.result_code) {
//                                "DATA OK" -> {
                                    // 내부에 토큰 저장
                                    sessionManager.saveAccessToken(response.body()?.access_token.toString())
                                    sessionManager.saveRefreshToken(response.body()?.refresh_token.toString())

                                    // 내부에 로그인 정보 저장
                                    val nickname = response.body()?.nickname
                                    sessionManager.saveUserInfo(nickname.toString())

                                    activity?.supportFragmentManager?.let { it1 -> preferencesDialog.show(it1, "Check and Change Password") }

                                    activity?.supportFragmentManager?.beginTransaction()?.apply {
                                        replace(R.id.frameLayout, PreferencesPasswordModificationFragment())
                                    }?.commit()
//                                }
//                                "ERROR" -> {
//                                    activity?.supportFragmentManager?.let { it1 -> preferencesDialog.show(it1, "Check Code") }
//                                }
//                            }
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

    inner class EditTextWatcher : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            if (!Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()) {
                email_text_error.visibility = View.VISIBLE
                send_email_button.isEnabled = false
                send_email_button.isClickable = false
                send_email_button.setBackgroundResource(R.drawable.button_shape_disabled)
                send_email_button.setTextColor(Color.DKGRAY)
            } else {
                email_text_error.visibility = View.INVISIBLE
                send_email_button.isEnabled = true
                send_email_button.isClickable = true
                send_email_button.setBackgroundResource(R.drawable.button_shape)
                send_email_button.setTextColor(Color.WHITE)
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    }

    inner class EditTextWatcher2 : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            if (password_text.toString().trimmedLength() != 0) {
                continue_button.isEnabled = true
                continue_button.isClickable = true
                continue_button.setBackgroundResource(R.drawable.button_shape)
                continue_button.setTextColor(Color.WHITE)

                return
            } else {
                continue_button.isEnabled = false
                continue_button.isClickable = false
                continue_button.setBackgroundResource(R.drawable.button_shape_disabled)
                continue_button.setTextColor(Color.DKGRAY)
            }
        }
    }
}
