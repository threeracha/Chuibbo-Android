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
import com.example.chuibbo_android.download.DownloadFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.face_correction_fragment.*
import kotlinx.android.synthetic.main.main_activity.*

class FaceCorrectionFragment : Fragment() {
    private lateinit var next_button: ImageButton

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.face_correction_fragment, container, false)

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.correction_contents, FaceCorrectionInsideFragment())
        }?.commit()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next_button = ImageButton(activity?.applicationContext)
        next_button.setImageResource(R.drawable.ic_arrow_right)
        val l3 =
            androidx.appcompat.widget.Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT
            )
        next_button.setBackgroundColor(Color.WHITE)
        next_button.layoutParams = l3
        next_button.setBackground(null)
        activity?.toolbar!!.addView(next_button)
        // This is how to set layout_gravity properties to Button
        // must be put this code after put button view on the activity
        (next_button.layoutParams as androidx.appcompat.widget.Toolbar.LayoutParams)?.apply {
            this.gravity = Gravity.RIGHT
        }
        next_button.setOnClickListener {
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.frameLayout, DownloadFragment())
                .commit()
            activity?.toolbar!!.removeView(next_button)
        }

        tablayout_face_correction.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val pos = tab.getPosition()
                changeView(pos)
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
