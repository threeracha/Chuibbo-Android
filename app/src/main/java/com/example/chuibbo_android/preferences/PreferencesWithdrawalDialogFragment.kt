package com.example.chuibbo_android.preferences

import android.app.Dialog
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
import com.example.chuibbo_android.auth.SessionManager
import kotlinx.android.synthetic.main.dialog_fragment.view.*
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreferencesWithdrawalDialogFragment : DialogFragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.dialog_fragment, container, false)

        view.dialog_message.text = "정말 탈퇴 하시겠습니까?"

        val fragment: Fragment? = activity?.supportFragmentManager?.findFragmentByTag("Withdrawal")

        sessionManager = SessionManager(requireContext())

        view.dialog_yes.setOnClickListener {
            runBlocking {
                UserApi.instance(requireContext()).withdraw().enqueue(object : Callback<SpringResponse<String>> {
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
                                    sessionManager.removeAccessToken()
                                    sessionManager.removeRefreshToken()
                                    sessionManager.removeUserInfo()

                                    activity?.supportFragmentManager?.beginTransaction()?.apply {
                                        replace(R.id.frameLayout, HomeFragment())
                                    }?.commit()

                                    val dialogFragment: DialogFragment = fragment as DialogFragment
                                    dialogFragment.dismiss()
                                }
                                "ERROR" -> {
                                    val dialogFragment: DialogFragment = fragment as DialogFragment
                                    dialogFragment.dismiss()
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
