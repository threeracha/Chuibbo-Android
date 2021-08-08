package com.example.chuibbo_android.background

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
import androidx.fragment.app.setFragmentResultListener
import com.example.chuibbo_android.R
import com.example.chuibbo_android.correction.FaceCorrectionFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.background_synthesis_fragment.*
import kotlinx.android.synthetic.main.main_activity.*

class BackgroundSynthesisFragment : Fragment() {
    private lateinit var next_button: ImageButton

    @SuppressLint("ResourceAsColor")
    private fun changeView(index: Int) {
        when (index) {
            0 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.color_contents, BackgroundSynthesisSolidcolorFragment())
                }?.commit()
            }
            1 -> {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.color_contents, BackgroundSynthesisGradationFragment())
                }?.commit()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // color picker로 선택된 컬러로 배경 색상 변경
        setFragmentResultListener("Picked Color") { resultKey, bundle ->
            background_synthesis_image.setBackgroundColor(Color.parseColor("#" + bundle.getString("color")))
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.background_synthesis_fragment, container, false)

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.color_contents, BackgroundSynthesisSolidcolorFragment())
        }?.commit()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next_button = ImageButton(context)
        next_button.setImageResource(R.drawable.ic_arrow_right)
        val l3 = androidx.appcompat.widget.Toolbar.LayoutParams(
            Toolbar.LayoutParams.WRAP_CONTENT,
            Toolbar.LayoutParams.WRAP_CONTENT,
        )
        next_button.layoutParams = l3
        next_button.setBackground(null)
        activity?.toolbar!!.addView(next_button)
        (next_button.layoutParams as androidx.appcompat.widget.Toolbar.LayoutParams).apply {
            this.gravity = Gravity.RIGHT
        }
        next_button.setOnClickListener {
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.frameLayout, FaceCorrectionFragment())
                .commit()
            activity?.toolbar!!.removeView(next_button)
        }

        background_synthesis_image_tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
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
