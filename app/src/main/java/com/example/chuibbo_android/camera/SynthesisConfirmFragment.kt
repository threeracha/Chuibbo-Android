package com.example.chuibbo_android.camera

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chuibbo_android.R
import com.example.chuibbo_android.background.BackgroundSynthesisFragment
import com.example.chuibbo_android.background.BackgroundSynthesisSolidcolorFragment
import com.example.chuibbo_android.image.Adapter
import com.example.chuibbo_android.image.ImageViewModel
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.overall_synthesis_confirm_fragment.*

class SynthesisConfirmFragment : Fragment() {

    lateinit var vm : ImageViewModel
    lateinit var next_button: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.overall_synthesis_confirm_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar!!.setTitle("사진 편집")

        val ad = Adapter()
        vm = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ImageViewModel::class.java)

        // TODO: 2021/03/29 Recycler View Binding 아닌, ImageView 하나의 Object에 바인딩 하기
        recycerlview_img.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ad
        }

        // sy: arrow_right_at_synthesis 버튼과 BackgroundSynthesisFragment 연결
        next_button = Button(context)
        next_button.setBackgroundResource(R.drawable.arrow_right_at_synthesis) // TODO: 버튼 크기 조정
        val l3 = androidx.appcompat.widget.Toolbar.LayoutParams(
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        Toolbar.LayoutParams.WRAP_CONTENT,
                )
        next_button.layoutParams = l3
        activity?.toolbar!!.addView(next_button)
        (next_button.layoutParams as androidx.appcompat.widget.Toolbar.LayoutParams).apply {
            this.gravity = Gravity.RIGHT
        }
        next_button.setOnClickListener {
            activity?.supportFragmentManager!!.beginTransaction()
            .replace(R.id.frameLayout, BackgroundSynthesisFragment())
            .commit()

            activity?.toolbar!!.removeView(next_button)
        }


        vm.allItem.observe(
            viewLifecycleOwner, Observer {
                it.let {
                    ad.addCategoryList(it)
                }
            }
        )
    }
}