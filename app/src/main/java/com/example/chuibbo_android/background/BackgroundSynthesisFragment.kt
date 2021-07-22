package com.example.chuibbo_android.background

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        // this is for next button on the last page
        // btn = ImageButton(context)
        var btn: ImageButton = ImageButton(activity?.applicationContext)
        btn.setImageResource(R.drawable.ic_arrow_right)
        val l3 =
            androidx.appcompat.widget.Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT
            )
        btn.setBackgroundColor(Color.WHITE)
        btn.layoutParams = l3
        btn.setBackground(null)
        btn.setOnClickListener {
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.frameLayout, FaceCorrectionFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        activity?.toolbar!!.addView(btn)
        // This is how to set layout_gravity properties to Button
        // must be put this code after put button view on the activity
        (btn.layoutParams as androidx.appcompat.widget.Toolbar.LayoutParams)?.apply {
            this.gravity = Gravity.RIGHT
        }

        background_synthesis_image_tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                var pos = tab.getPosition()
                changeView(pos)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) { }

            override fun onTabReselected(tab: TabLayout.Tab) { }
        })
    }
}
