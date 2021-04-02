package com.example.chuibbo_android.correction

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.face_correction_fragment.*

class FaceCorrectionFragment : Fragment() {

    @SuppressLint("ResourceAsColor")
    private fun changeView(index: Int) {
        when (index) {
            0 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.correction_contents, FaceCorrectionInsideFragment())
                }?.commit()
            }
            1 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.correction_contents, FaceCorrectionMakeupFragment())
                }?.commit()
            }
            2 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.correction_contents, FaceCorrectionDistortionFragment())
                }?.commit()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.face_correction_fragment, container, false)

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.correction_contents, FaceCorrectionInsideFragment())
        }?.commit()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tablayout_face_correction.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                var pos = tab.getPosition()
                changeView(pos)
                // TODO: tab 선택시 폰트 크기 증가, bold, 높이 증가
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // do nothing
            }
        })
    }
}