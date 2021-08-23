package com.example.chuibbo_android.mypage

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
import com.example.chuibbo_android.home.ImageLoader
import kotlinx.android.synthetic.main.mypage_myalbum_dialog_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MypageMyalbumDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.mypage_myalbum_dialog_fragment, container, false)

        var fragment: Fragment? = activity?.supportFragmentManager?.findFragmentByTag("CustomDialog")
        val args = arguments
        if (args != null) {
            view.photo_desc.text = args.getString("dateAndDesc")
            val s = args.getString("image")

            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = withContext(Dispatchers.IO) {
                    s?.let { ImageLoader.loadImage(it) }
                }
                view.dialog_photo.setImageBitmap(bitmap)
            }
        }

        view.close_button.setOnClickListener {
            var dialogFragment: DialogFragment = fragment as DialogFragment
            dialogFragment.dismiss()
        }
        view.dialog_save.setOnClickListener {
            var dialogFragment: DialogFragment = fragment as DialogFragment
            dismiss() // TODO: 저장으로 변경
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
