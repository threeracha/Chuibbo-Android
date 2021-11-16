package com.example.chuibbo_android.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.UserApi
import com.example.chuibbo_android.api.response.ApiResponse
import com.example.chuibbo_android.api.response.UserResponse
import com.example.chuibbo_android.home.HomeFragment
import com.example.chuibbo_android.preferences.PreferencesPasswordForgetFragment
import com.example.chuibbo_android.signup.SignupFragment
import com.example.chuibbo_android.utils.SessionManager
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar_title!!.text = "로그인"

        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var loginFailureDialog: LoginFailureDialogFragment = LoginFailureDialogFragment()
        loginFailureDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기

        sessionManager = SessionManager(requireContext())

        login_button.setOnClickListener {

            val email = email_edit_text.text.toString()
            val password = password_edit_text.text.toString()

            var loginInfo = hashMapOf("email" to email)
            loginInfo["password"] = password

            runBlocking {
                UserApi.instance(requireContext()).login(
                    data = loginInfo
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

                                    // TODO: 로그인 성공! 홈으로 가기
                                    activity?.supportFragmentManager?.beginTransaction()?.apply {
                                        replace(R.id.frameLayout, HomeFragment())
                                    }?.commit()
//                                }
//                                "ERROR" -> {
//                                    activity?.supportFragmentManager?.let { it1 -> loginFailureDialog.show(it1, "Login Failure") }
//                                }
//                            }
                        }
                    }
                })
            }
        }

        question_password_text.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesPasswordForgetFragment())
                addToBackStack(null)
            }?.commit()
        }

        make_account_text.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, SignupFragment())
                addToBackStack(null)
            }?.commit()
        }

        google_social_login_image.setOnClickListener {
            runBlocking {
                UserApi.instance(requireContext()).loginGoogle(
                ).enqueue(object : Callback<ApiResponse<String>> {
                    override fun onFailure(call: Call<ApiResponse<String>>, t: Throwable) {
                        Log.d("retrofit fail", t.message)
                    }

                    override fun onResponse(
                        call: Call<ApiResponse<String>>,
                        response: Response<ApiResponse<String>>
                    ) {
                        if (response.isSuccessful) {
                            when (response.body()?.success) {
                                true -> {
                                    val transaction = activity?.supportFragmentManager!!.beginTransaction()
                                    transaction.replace(R.id.frameLayout, HomeFragment())
                                }
                            }
                        }
                    }
                })
            }
        }

        naver_social_login_image.setOnClickListener {

        }
    }
}
