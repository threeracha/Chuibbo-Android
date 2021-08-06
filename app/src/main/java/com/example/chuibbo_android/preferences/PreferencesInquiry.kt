package com.example.chuibbo_android.preferences

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.trimmedLength
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.preferences_inquiry_fragment.view.*

class PreferencesInquiry : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.preferences_inquiry_fragment, container, false)

        activity?.back_button!!.visibility = View.VISIBLE

        view.inquiry_contents.addTextChangedListener(object : TextWatcher { // 문의하기 내용 입력시 버튼 활성화
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (view.inquiry_contents.text.toString().trimmedLength() != 0) {
                    view.inquiry_button.isEnabled = true
                    view.inquiry_button.isClickable = true
                    view.inquiry_button.setBackgroundResource(R.drawable.button_shape)
                    view.inquiry_button.setTextColor(Color.WHITE)
                } else {
                    view.inquiry_button.isEnabled = false
                    view.inquiry_button.isClickable = false
                    view.inquiry_button.setBackgroundResource(R.drawable.button_shape_disabled)
                    view.inquiry_button.setTextColor(Color.DKGRAY)
                }
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar_title!!.text = "문의하기"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
    }
}
