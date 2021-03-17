package com.example.chuibbo_android.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.signup.SignupFragment
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dialog: LoginFailureDialogFragment = LoginFailureDialogFragment()
        dialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기

        login_button.setOnClickListener {
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "Login Failure") }
        }

        make_account_text.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, SignupFragment())
                addToBackStack(null)
            }?.commit()
        }
    }
}