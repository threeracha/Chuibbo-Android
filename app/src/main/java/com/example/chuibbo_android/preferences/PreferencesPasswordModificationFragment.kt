package com.example.chuibbo_android.preferences

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.UserApi
import com.example.chuibbo_android.api.response.SpringResponse
import com.example.chuibbo_android.home.HomeFragment
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.preferences_password_modification_fragment.*
import kotlinx.android.synthetic.main.preferences_password_modification_fragment.password_text
import kotlinx.android.synthetic.main.preferences_password_modification_fragment.password_check_text
import kotlinx.android.synthetic.main.preferences_password_modification_fragment.password_text_error
import kotlinx.android.synthetic.main.preferences_password_modification_fragment.password_check_text_error
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreferencesPasswordModificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar_title!!.text = "비밀번호변경"
        activity?.back_button!!.visibility = View.VISIBLE

        return inflater.inflate(R.layout.preferences_password_modification_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 비밀번호 제약 확인
        password_text.addTextChangedListener(CheckPassword())

        // 비밀번호와 비밀번호 체크 동일한지 확인
        password_text.addTextChangedListener(CheckPasswordMatch())
        password_check_text.addTextChangedListener(CheckPasswordMatch())

        var preferencesDialog: PreferencesDialogFragment = PreferencesDialogFragment()
        preferencesDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기

        ok_button.setOnClickListener {
            val password = password_text.text.toString().toRequestBody()

            runBlocking {
                UserApi.instance(requireContext()).changePassword(
                    password = password
                ).enqueue(object : Callback<SpringResponse<String>> {
                    override fun onFailure(call: Call<SpringResponse<String>>, t: Throwable) {
                        Log.d("retrofit fail", t.message)
                        // TODO: 서버는 처리되었는데 fail임. 왜?
                    }

                    override fun onResponse(
                        call: Call<SpringResponse<String>>,
                        response: Response<SpringResponse<String>>
                    ) {
                        if (response.isSuccessful) {
                            when (response.body()?.result_code) {
                                "DATA OK" -> {
                                    activity?.supportFragmentManager?.let { it1 -> preferencesDialog.show(it1, "Change Password Success") }

                                    activity?.supportFragmentManager?.beginTransaction()?.apply {
                                        replace(R.id.frameLayout, HomeFragment())
                                    }?.commit()
                                }
                                "ERROR" -> {
                                    activity?.supportFragmentManager?.let { it1 -> preferencesDialog.show(it1, "Change Password Failure") }
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
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            if (!isPasswordFormat(s.toString()))
                password_text_error.visibility = View.VISIBLE
            else {
                password_text_error.visibility = View.INVISIBLE
            }
        }

        private fun isPasswordFormat(password: String): Boolean {
            return password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[?!@#\$%^&*]).{8,15}\$".toRegex())
        }
    }

    inner class CheckPasswordMatch : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            if (password_check_text.text.toString().isNullOrBlank()) {
                password_check_text_error.visibility = View.INVISIBLE
                ok_button.isEnabled = false
                ok_button.setBackgroundResource(R.drawable.button_shape_disabled)
                ok_button.setTextColor(Color.DKGRAY)
            }
            else if (password_check_text.text.toString() != password_text.text.toString()) {
                password_check_text_error.visibility = View.VISIBLE
                ok_button.isEnabled = false
                ok_button.setBackgroundResource(R.drawable.button_shape_disabled)
                ok_button.setTextColor(Color.DKGRAY)
            }
            else {
                password_check_text_error.visibility = View.INVISIBLE
                ok_button.isEnabled = true
                ok_button.setBackgroundResource(R.drawable.button_shape)
                ok_button.setTextColor(Color.WHITE)
            }
        }
    }

}
