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
import com.example.chuibbo_android.home.HomeFragment
import com.example.chuibbo_android.preferences.PreferencesPasswordForgetFragment
import com.example.chuibbo_android.signup.SignupFragment
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

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

        login_button.setOnClickListener {

            val email = email_edit_text.text.toString().toRequestBody(("text/plain").toMediaTypeOrNull())
            val password = password_edit_text.text.toString().toRequestBody(("text/plain").toMediaTypeOrNull())

            var loginInfo = hashMapOf("email" to email)
            loginInfo["password"] = password

            runBlocking {
                UserApi.instance.login(
                    data = loginInfo
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
                                    // TODO: 로그인 성공! 홈으로 가기
                                    val transaction = activity?.supportFragmentManager!!.beginTransaction()
                                    transaction.replace(R.id.frameLayout, HomeFragment())
                                }
                                false -> {
                                    activity?.supportFragmentManager?.let { it1 -> loginFailureDialog.show(it1, "Login Failure") }
                                }
                            }
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
                UserApi.instance.loginGoogle(
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
