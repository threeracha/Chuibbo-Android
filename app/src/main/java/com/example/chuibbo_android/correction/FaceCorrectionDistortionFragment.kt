package com.example.chuibbo_android.correction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R

class FaceCorrectionDistortionFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.face_correction_distortion_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
    }
}