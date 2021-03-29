package com.example.chuibbo_android.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chuibbo_android.R
import com.example.chuibbo_android.image.Adapter
import com.example.chuibbo_android.image.ImageViewModel
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.synthesis_fragment.*

class SynthesisFragment : Fragment() {

    lateinit var vm : ImageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.synthesis_fragment, container, false)
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

        vm.allItem.observe(
            viewLifecycleOwner, Observer {
                it.let {
                    ad.addCategoryList(it)
                }
            }
        )
    }
}