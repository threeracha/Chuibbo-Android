package com.example.chuibbo_android.login

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
import kotlinx.android.synthetic.main.login_failure_dialog_fragment.view.*

class LoginFailureDialogFragment: DialogFragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.login_failure_dialog_fragment, container, false)

        var fragment: Fragment? = activity?.supportFragmentManager?.findFragmentByTag("Login Failure")
        view.dialog_ok.setOnClickListener {
            var dialogFragment: DialogFragment = fragment as DialogFragment
            dialogFragment.dismiss()
        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}