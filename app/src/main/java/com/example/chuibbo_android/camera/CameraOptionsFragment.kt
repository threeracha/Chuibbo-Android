package com.example.chuibbo_android.camera

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.camera_option_fragment.*
import kotlinx.android.synthetic.main.guideline_fragment_contents.*

class CameraOptionsFragment : Fragment() {
    private lateinit var callback: OnBackPressedCallback
    private var face1_flag: Boolean = false
    private var face2_flag: Boolean = false
    private var hair1_flag: Boolean = false
    private var hair2_flag: Boolean = false
    private var hair3_flag: Boolean = false
    private var suit1_flag: Boolean = false
    private var suit2_flag: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.camera_option_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_face1.setImageResource(R.drawable.round_face)
        img_face2.setImageResource(R.drawable.long_face)

        img_face1.setOnClickListener {
            controlOption(img_face1, face1_flag)
            face1_flag = !face1_flag
        }

        img_face2.setOnClickListener {
            controlOption(img_face2, face2_flag)
            face2_flag = !face2_flag
        }

        arguments?.let {
            when(it.getInt(ARGS_PAGER_POSITION)) {
                0 -> { // 여
                    img_hair1.setImageResource(R.drawable.female_short_hair)
                    img_hair2.setImageResource(R.drawable.female_mid_hair)
                    img_hair3.setImageResource(R.drawable.female_long_hair)

                    img_suit1.setImageResource(R.drawable.female_suit)

                    img_hair1.setOnClickListener {
                        controlOption(img_hair1, hair1_flag)
                        hair1_flag = !hair1_flag
                    }

                    img_hair2.setOnClickListener {
                        controlOption(img_hair2, hair2_flag)
                        hair2_flag = !hair2_flag
                    }

                    img_hair3.setOnClickListener {
                        controlOption(img_hair3, hair3_flag)
                        hair3_flag = !hair3_flag
                    }

                    img_suit1.setOnClickListener {
                        controlOption(img_suit1, suit1_flag)
                        suit1_flag = !suit1_flag
                    }
                }
                1 -> { // 남
                    img_hair1.setImageResource(R.drawable.male_no_bang)
                    img_hair2.setImageResource(R.drawable.male_half_bang)
                    img_hair3.setImageResource(R.drawable.male_bang)

                    img_suit1.setImageResource(R.drawable.male_suit1)
                    img_suit2.setImageResource(R.drawable.male_suit2)

                    img_hair1.setOnClickListener {
                        controlOption(img_hair1, hair1_flag)
                        hair1_flag = !hair1_flag
                    }

                    img_hair2.setOnClickListener {
                        controlOption(img_hair2, hair2_flag)
                        hair2_flag = !hair2_flag
                    }

                    img_hair3.setOnClickListener {
                        controlOption(img_hair3, hair3_flag)
                        hair3_flag = !hair3_flag
                    }

                    img_suit1.setOnClickListener {
                        controlOption(img_suit1, suit1_flag)
                        suit1_flag = !suit1_flag
                    }

                    img_suit2.setOnClickListener {
                        controlOption(img_suit2, suit2_flag)
                        suit2_flag = !suit2_flag
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager!!.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    private fun controlOption(optionItem: ImageView, currentFlag: Boolean) {
        when (currentFlag) {
            false -> {
                optionItem.setPadding(6,6,6,6)
                optionItem.setBackgroundResource(R.drawable.selected_option_border)
            }
            true -> {
                optionItem.setPadding(0,0,0,0)
            }
        }
    }


    companion object {
        private const val ARGS_PAGER_POSITION = "args_pager_position"

        @JvmStatic
        fun newIntent(position: Int): CameraOptionsFragment {
            return CameraOptionsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARGS_PAGER_POSITION, position)
                }
            }
        }
    }
}

