package com.example.chuibbo_android.signup

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.failure_dialog_fragment.view.*

class SignupDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.failure_dialog_fragment, container, false)

        lateinit var fragment: Fragment

        if (activity?.supportFragmentManager?.findFragmentByTag("Nickname Check OK") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Nickname Check OK")!!
            view.dialog_message.text = "닉네임을 사용할 수 있습니다."

        } else if (activity?.supportFragmentManager?.findFragmentByTag("Nickname Check ERROR") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Nickname Check ERROR")!!
            view.dialog_message.text = "이미 존재하는 닉네임 입니다."

        } else if (activity?.supportFragmentManager?.findFragmentByTag("Email Check OK") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Email Check OK")!!
            view.dialog_message.text = "이메일을 사용할 수 있습니다."

        } else if (activity?.supportFragmentManager?.findFragmentByTag("Email Check ERROR") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Email Check ERROR")!!
            view.dialog_message.text = "이미 존재하는 이메일 입니다."

        } else if (activity?.supportFragmentManager?.findFragmentByTag("Signup Failure") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Signup Failure")!!
            view.dialog_message.text = "회원가입에 실패하였습니다."
        }

        view.dialog_ok.setOnClickListener {
            var dialogFragment: DialogFragment = fragment as DialogFragment
            dialogFragment.dismiss()
        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}
