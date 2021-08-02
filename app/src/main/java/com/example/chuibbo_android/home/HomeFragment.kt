package com.example.chuibbo_android.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.main_activity.*

class HomeFragment : Fragment() {

    private lateinit var sliderViewPager: ViewPager2
    private lateinit var layoutIndicator: LinearLayout

    private val images = arrayOf(
        "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
        "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // TODO: company_desc가 일정 길이 이상이면 자르기
        // dummy data to populate the RecyclerView with
        var data = ArrayList<JobPostingModel>()
        data.add(
            JobPostingModel(
                "롯데제과",
                "롯데제과 채용공고",
                3,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "https://www.lotteconf.co.kr/"
            )
        )
        data.add(
            JobPostingModel(
                "두산",
                "두산 채용",
                2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
                "https://www.doosan.com/kr"
            )
        )
        data.add(
            JobPostingModel(
                "두산",
                "두산 채용",
                2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
                "https://www.doosan.com/kr"
            )
        )

        val view = inflater.inflate(R.layout.home_fragment, container, false)

        val recyclerView: RecyclerView = view.recyclerview
        val numberOfColumns = 2
        recyclerView.layoutManager = GridLayoutManager(context, numberOfColumns)
        var adapter = HomeJobPostingRecyclerViewAdapter(context, data)
        // adapter.setClickListener(this)
        recyclerView.adapter = adapter

        // TODO: star 클릭시 color 변화

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar_title!!.text = "취뽀"

        sliderViewPager = view.banner_pager
        layoutIndicator = view.banner_indicator

        sliderViewPager.setOffscreenPageLimit(1)
        sliderViewPager.setAdapter(ImageSliderAdapter(context, images))

        sliderViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        setupIndicators(images.size)
    }

    private fun setupIndicators(count: Int) {
        val indicators: Array<ImageView?> = arrayOfNulls<ImageView>(count)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(16, 8, 16, 8)
        for (i in indicators.indices) {
            indicators[i] = ImageView(context)
            indicators[i]?.setImageDrawable(
                context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.guideline_indicator_unselected
                    )
                }
            )
            indicators[i]?.setLayoutParams(params)
            layoutIndicator.addView(indicators[i])
        }
        setCurrentIndicator(0)
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = layoutIndicator.childCount
        for (i in 0 until childCount) {
            val imageView: ImageView = layoutIndicator.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.guideline_indicator
                        )
                    }
                )
            } else {
                imageView.setImageDrawable(
                    context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.guideline_indicator_unselected
                        )
                    }
                )
            }
        }
    }
}
