package com.example.chuibbo_android.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.main_activity.*

class HomeFragment : Fragment() {

    private val jobPostListViewModel by viewModels<JobPostListViewModel> {
        context?.let { JobPostListViewModelFactory(it) }!!
    }
    private lateinit var sliderViewPager: ViewPager2
    private lateinit var layoutIndicator: LinearLayout

    private val images = arrayOf(
        "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
        "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.home_fragment, container, false)

        // TODO: company_desc가 일정 길이 이상이면 자르기

        val jobPostAdapter = JobPostAdapter { jobPost -> adapterOnClick(jobPost, view) }

        val recyclerView: RecyclerView = view.recyclerview
        val numberOfColumns = 2
        recyclerView.layoutManager = GridLayoutManager(context, numberOfColumns)
        recyclerView.adapter = jobPostAdapter

        jobPostListViewModel.jobPostsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                jobPostAdapter.submitList(it as MutableList<JobPost>)
            }
        })


        view.more.setOnClickListener(View.OnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, HomeJobPostMoreFragment())
                addToBackStack(null)
            }?.commit()
        })

        view.plus.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, HomeJobPostMoreFragment())
                addToBackStack(null)
            }?.commit()
        }

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

    /* Opens companyLink of JobPost when RecyclerView item is clicked. */
    private fun adapterOnClick(jobPost: JobPost, view: View) {
        val url: String = jobPost.companyLink
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        view.context.startActivity(intent)
    }

//    private fun adapterStarOnClick(jobPost: JobPost, view: View) {
//        view.star.setImageResource(R.drawable.ic_star_fill)
//    }

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
