package com.example.chuibbo_android.camera

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.chuibbo_android.R
import com.example.chuibbo_android.background.BackgroundSynthesisFragment
import com.example.chuibbo_android.image.Adapter
import com.example.chuibbo_android.image.ImageViewModel
import kotlinx.android.synthetic.main.download_fragment.view.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.overall_synthesis_confirm_fragment.*
import kotlinx.android.synthetic.main.overall_synthesis_confirm_item.*
import kotlinx.android.synthetic.main.overall_synthesis_confirm_item.view.*

class SynthesisConfirmFragment : Fragment() {

    private lateinit var vm: ImageViewModel
    private val adapter = Adapter()
    private lateinit var result: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.back_button!!.visibility = View.VISIBLE
        activity?.process1!!.visibility = View.VISIBLE
        activity?.btn_next!!.visibility = View.VISIBLE

        return inflater.inflate(R.layout.overall_synthesis_confirm_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar_title!!.text = ""

        vm = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ImageViewModel::class.java)

        // TODO: 2021/03/29 Recycler View Binding 아닌, ImageView 하나의 Object에 바인딩 하기
        // 리사이클러뷰 적용 보류
//        recycerlview_img.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            adapter = ad
//        }

        setFragmentResultListener("requestBitmapKey") { key, bundle ->
            result = bundle.getParcelable<Bitmap>("bundleBitmapKey")!!
            img_synthesis!!.setImageBitmap(result)
        }

        activity?.btn_next!!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, BackgroundSynthesisFragment())
                addToBackStack(null)
            }?.commit()
        }

        vm.allItem.observe(viewLifecycleOwner) { adapter.addCategoryList(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
        activity?.process1!!.visibility = View.GONE
        activity?.btn_next!!.visibility = View.GONE
    }
}
