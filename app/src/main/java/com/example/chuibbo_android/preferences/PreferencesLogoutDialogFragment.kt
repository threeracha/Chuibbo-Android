package com.example.chuibbo_android.preferences

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.UserApi
import com.example.chuibbo_android.api.response.SpringResponse
import com.example.chuibbo_android.home.HomeFragment
import kotlinx.android.synthetic.main.dialog_fragment.view.*
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreferencesLogoutDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.dialog_fragment, container, false)

        view.dialog_message.text = "정말 로그아웃 하시겠습니까?"

        val fragment: Fragment? = activity?.supportFragmentManager?.findFragmentByTag("Logout")
        view.dialog_yes.setOnClickListener {
            val preferences = activity?.getSharedPreferences(
                "MY_APP",
                Context.MODE_PRIVATE
            )
            val access_token = preferences?.getString("access_token", "")

            val dialogFragment: DialogFragment = fragment as DialogFragment
            dialogFragment.dismiss()

            runBlocking {
                UserApi.instance.logout(
                    access_token!!
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
                                    preferences?.edit()?.remove("access_token")?.apply()

                                    activity?.supportFragmentManager?.beginTransaction()?.apply {
                                        replace(R.id.frameLayout, HomeFragment())
                                        addToBackStack(null)
                                    }?.commit()
                                }
                                "ERROR" -> {

                                }
                            }
                        }
                    }
                })
            }
        }

        view.dialog_no.setOnClickListener {
            val dialogFragment: DialogFragment = fragment as DialogFragment
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
