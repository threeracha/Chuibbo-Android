package com.example.chuibbo_android.camera

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.option.Option
import kotlinx.android.synthetic.main.camera_option_fragment.*

enum class Sex(val type: Int) {MALE(0), FEMALE(1)}
enum class FaceType(val type: Int) {ROUND(0), LONG(1)}
enum class Hairstyle(val style: String) {
    BANG("bang"), HALF_BANG("half_bang"), NO_BANG("no_bang"),
    LONG("long"), MID("mid"), SHORT("short")
}

class CameraOptionsFragment : Fragment() {
    private lateinit var callback: OnBackPressedCallback

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
            controlOption(img_face1, true)
            controlOption(img_face2, false)
            Option.Opt.opt.face_shape = FaceType.ROUND
        }

        img_face2.setOnClickListener {
            controlOption(img_face2, true)
            controlOption(img_face1, false)
            Option.Opt.opt.face_shape = FaceType.LONG
        }

        arguments?.let {
            when (it.getInt(ARGS_PAGER_POSITION)) {
                0 -> { // 여
                    Option.Opt.opt.sex = Sex.FEMALE
                    img_hair1.setImageResource(R.drawable.female_short_hair)
                    img_hair2.setImageResource(R.drawable.female_mid_hair)
                    img_hair3.setImageResource(R.drawable.female_long_hair)

                    img_suit1.setImageResource(R.drawable.female_suit)

                    img_hair1.setOnClickListener {
                        controlOption(img_hair1, true)
                        controlOption(img_hair2, false)
                        controlOption(img_hair3, false)
                        Option.Opt.opt.hairstyle = Hairstyle.SHORT
                    }

                    img_hair2.setOnClickListener {
                        controlOption(img_hair2, true)
                        controlOption(img_hair1, false)
                        controlOption(img_hair3, false)
                        Option.Opt.opt.hairstyle = Hairstyle.MID
                    }

                    img_hair3.setOnClickListener {
                        controlOption(img_hair3, true)
                        controlOption(img_hair1, false)
                        controlOption(img_hair2, false)
                        Option.Opt.opt.hairstyle = Hairstyle.LONG
                    }

                    img_suit1.setOnClickListener {
                        controlOption(img_suit1, true)
                        Option.Opt.opt.suit = 0
                    }
                }
                1 -> { // 남
                    Option.Opt.opt.sex = Sex.MALE
                    img_hair1.setImageResource(R.drawable.male_no_bang)
                    img_hair2.setImageResource(R.drawable.male_half_bang)
                    img_hair3.setImageResource(R.drawable.male_bang)

                    img_suit1.setImageResource(R.drawable.male_suit1)
                    img_suit2.setImageResource(R.drawable.male_suit2)

                    img_hair1.setOnClickListener {
                        controlOption(img_hair1, true)
                        controlOption(img_hair2, false)
                        controlOption(img_hair3, false)
                        Option.Opt.opt.hairstyle = Hairstyle.NO_BANG
                    }

                    img_hair2.setOnClickListener {
                        controlOption(img_hair2, true)
                        controlOption(img_hair1, false)
                        controlOption(img_hair3, false)
                        Option.Opt.opt.hairstyle = Hairstyle.HALF_BANG
                    }

                    img_hair3.setOnClickListener {
                        controlOption(img_hair3, true)
                        controlOption(img_hair1, false)
                        controlOption(img_hair2, false)
                        Option.Opt.opt.hairstyle = Hairstyle.BANG
                    }

                    img_suit1.setOnClickListener {
                        controlOption(img_suit1, true)
                        controlOption(img_suit2, false)
                        Option.Opt.opt.suit = 0
                    }

                    img_suit2.setOnClickListener {
                        controlOption(img_suit2, true)
                        controlOption(img_suit1, false)
                        Option.Opt.opt.suit = 1
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
            true -> {
                optionItem.setPadding(6, 6, 6, 6)
                optionItem.setBackgroundResource(R.drawable.selected_option_border)
            }
            false -> {
                optionItem.setPadding(0, 0, 0, 0)
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
