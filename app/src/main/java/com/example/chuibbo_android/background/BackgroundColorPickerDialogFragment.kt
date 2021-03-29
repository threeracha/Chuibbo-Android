package com.example.chuibbo_android.background

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.skydoves.colorpickerview.listeners.ColorListener
import kotlinx.android.synthetic.main.background_color_picker_dialog_fragment.*
import kotlinx.android.synthetic.main.background_synthesis_fragment.*

class BackgroundColorPickerDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(com.example.chuibbo_android.R.layout.background_color_picker_dialog_fragment, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var colorpickerdialog = ColorPickerDialog.Builder(context)
            .setTitle("ColorPicker Dialog")
            .setPreferenceName("MyColorPickerDialog")
            .setPositiveButton("확인",
                ColorEnvelopeListener { envelope, fromUser -> background_synthesis_fragment.setBackgroundColor(envelope.color) })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
            .attachAlphaSlideBar(true) // the default value is true.
            .attachBrightnessSlideBar(true) // the default value is true.
            .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
            .show()

        return colorpickerdialog
    }

}