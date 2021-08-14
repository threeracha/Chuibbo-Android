package com.example.chuibbo_android.correction

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.background.BackgroundSynthesisFragment
import com.example.chuibbo_android.download.DownloadFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.face_correction_fragment.*
import kotlinx.android.synthetic.main.main_activity.*

class FaceCorrectionFragment : Fragment() {
    private lateinit var next_button: ImageButton

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.face_correction_fragment, container, false)

        activity?.toolbar_title!!.text = ""
        activity?.back_button!!.visibility = View.VISIBLE
        activity?.process3!!.visibility = View.VISIBLE
        activity?.btn_next!!.visibility = View.VISIBLE

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.correction_contents, FaceCorrectionInsideFragment())
        }?.commit()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.btn_next!!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, DownloadFragment())
                addToBackStack(null)
            }?.commit()
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
        activity?.process3!!.visibility = View.GONE
        activity?.btn_next!!.visibility = View.GONE
    }
}
