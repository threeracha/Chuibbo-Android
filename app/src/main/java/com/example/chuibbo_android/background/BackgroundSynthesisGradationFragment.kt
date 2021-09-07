package com.example.chuibbo_android.background

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.background_synthesis_gradation_fragment.*

class BackgroundSynthesisGradationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.background_synthesis_gradation_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()

        gradation_none.setOnClickListener { setFragmentResult("Picked Gradation", bundleOf("gradation" to 0)) }
        gradation1.setOnClickListener { setFragmentResult("Picked Gradation", bundleOf("gradation" to R.drawable.gradation1)) }
        gradation2.setOnClickListener { setFragmentResult("Picked Gradation", bundleOf("gradation" to R.drawable.gradation2)) }
        gradation3.setOnClickListener { setFragmentResult("Picked Gradation", bundleOf("gradation" to R.drawable.gradation3)) }
        gradation4.setOnClickListener { setFragmentResult("Picked Gradation", bundleOf("gradation" to R.drawable.gradation4)) }
        gradation5.setOnClickListener { setFragmentResult("Picked Gradation", bundleOf("gradation" to R.drawable.gradation5)) }
    }
}
