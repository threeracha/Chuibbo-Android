package com.example.chuibbo_android.camera

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
import com.example.chuibbo_android.camera.CameraFragment.Companion.isFromGallery
import com.example.chuibbo_android.utils.Common
import kotlinx.android.synthetic.main.face_detection_failfure_dialog_fragment.view.*

class FaceDetectionFailureDialogFragment : DialogFragment() {
    private val fragment: Fragment? = activity?.supportFragmentManager?.findFragmentByTag("Face Detection Failure")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dialogView: View = inflater.inflate(R.layout.face_detection_failfure_dialog_fragment, container, false)
        val common = Common(this, this.requireActivity())

        dialogView.btn_dialog_cancel2.setOnClickListener {
            dismiss()
        }
        dialogView.btn_dialog_again.setOnClickListener {
            // TODO: 다시 사진 선택했을 때 이미지 바뀌도록 수정
            dismiss()
            when (isFromGallery) {
                true -> {
                    common.dispatchGalleryIntent()
                }
                false -> {
                    common.dispatchTakePictureIntent()
                }
            }
        }
        return dialogView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}
