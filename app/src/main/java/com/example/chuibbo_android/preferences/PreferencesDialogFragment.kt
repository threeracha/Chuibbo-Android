package com.example.chuibbo_android.preferences

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

class PreferencesDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.failure_dialog_fragment, container, false)

        lateinit var fragment: Fragment

        if (activity?.supportFragmentManager?.findFragmentByTag("Check Email") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Check Email")!!
            view.dialog_message.text = "이메일이 존재하지 않습니다."

        }  else if (activity?.supportFragmentManager?.findFragmentByTag("Send Email") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Send Email")!!
            view.dialog_message.text = "임시 비밀번호를 전송하였습니다."

        } else if (activity?.supportFragmentManager?.findFragmentByTag("Send Email Failure") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Send Email Failure")!!
            view.dialog_message.text = "임시 비밀번호 전송에 실패하였습니다."

        } else if (activity?.supportFragmentManager?.findFragmentByTag("Check and Change Password") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Check and Change Password")!!
            view.dialog_message.text = "확인되었습니다. 새로운 비밀번호를 만들어주세요."

        } else if (activity?.supportFragmentManager?.findFragmentByTag("Check Code") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Check Code")!!
            view.dialog_message.text = "전송된 임시 비밀번호가 올바른지 다시 한 번 확인해주세요."

        } else if (activity?.supportFragmentManager?.findFragmentByTag("Change Password Success") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Change Password Success")!!
            view.dialog_message.text = "비밀번호 변경에 성공하였습니다."

        } else if (activity?.supportFragmentManager?.findFragmentByTag("Change Password Failure") != null) {
            fragment = activity?.supportFragmentManager?.findFragmentByTag("Change Password Failure")!!
            view.dialog_message.text = "비밀번호 변경에 실패하였습니다."
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
