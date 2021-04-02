package com.example.chuibbo_android.camera

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.camera_option_fragment.*

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

        arguments?.let {
            when(it.getInt(ARGS_PAGER_POSITION)) {
                0 -> { // 여
                    img_hair1.setImageResource(R.drawable.female_short_hair)
                    img_hair2.setImageResource(R.drawable.female_mid_hair)
                    img_hair3.setImageResource(R.drawable.female_long_hair)

                    img_suit1.setImageResource(R.drawable.female_suit)
                }
                1 -> { // 남
                    img_hair1.setImageResource(R.drawable.male_no_bang)
                    img_hair2.setImageResource(R.drawable.male_half_bang)
                    img_hair3.setImageResource(R.drawable.male_bang)

                    img_suit1.setImageResource(R.drawable.male_suit1)
                    img_suit1.setImageResource(R.drawable.male_suit2)
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

