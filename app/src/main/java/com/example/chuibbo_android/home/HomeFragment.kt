package com.example.chuibbo_android.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.JobPostApi
import com.example.chuibbo_android.api.response.SpringServerResponse
import com.example.chuibbo_android.mypage.LikeJobPostListViewModel
import com.example.chuibbo_android.mypage.LikeJobPostListViewModelFactory
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.home_job_posting.view.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.runBlocking
import me.relex.circleindicator.CircleIndicator3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var sliderViewPager: ViewPager2
    private lateinit var layoutIndicator: CircleIndicator3
    private lateinit var timer: Timer
    private var currentItem = 0
    private val DELAY_MS: Long = 0
    private val PERIOD_MS: Long = 3000

    private val jobPostListViewModel by viewModels<JobPostListViewModel> {
        context?.let { JobPostListViewModelFactory(it) }!!
    }

    private val jobPostMoreListViewModel by viewModels<JobPostMoreListViewModel> {
        context?.let { JobPostMoreListViewModelFactory(it) }!!
    }

    private val likeJobPostListViewModel by viewModels<LikeJobPostListViewModel> {
        context?.let { LikeJobPostListViewModelFactory(it) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.home_fragment, container, false)

        sliderViewPager = view.banner_pager
        sliderViewPager.offscreenPageLimit = 1
        sliderViewPager.adapter = context?.let { ImageSliderAdapter(it, getBannerImages()) }

        layoutIndicator = view.banner_indicator
        layoutIndicator.setViewPager(sliderViewPager)

        // PERIOD_MS초 마다 다음 배너 이미지로 전환
        val handler = Handler()
        val update = Runnable {
            if (currentItem === getBannerImages().size-1) {
                currentItem = -1
            }
            sliderViewPager.setCurrentItem(++currentItem, true)
        }

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)

        // TODO: 배너 이미지 마지막 위치시, 처음으로

        // recyclerView
        val jobPostAdapter = JobPostAdapter ({ jobPost -> adapterOnClick(jobPost, view) }, { jobPost, itemView -> adapterStarOnClick(jobPost, itemView) })

        val recyclerView: RecyclerView = view.recyclerview
        val numberOfColumns = 2
        recyclerView.layoutManager = GridLayoutManager(context, numberOfColumns)
        recyclerView.adapter = jobPostAdapter

        jobPostListViewModel.jobPostsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                jobPostAdapter.submitList(it as MutableList<JobPost>)
            }
        })

        runBlocking {
            JobPostApi.instance(requireContext()).getJobPosts().enqueue(object :
                Callback<SpringServerResponse<List<JobPost>>> {
                override fun onFailure(
                    call: Call<SpringServerResponse<List<JobPost>>>,
                    t: Throwable
                ) {
                    Log.d("retrofit fail", t.message)
                }

                override fun onResponse(
                    call: Call<SpringServerResponse<List<JobPost>>>,
                    response: Response<SpringServerResponse<List<JobPost>>>
                ) {
                    if (response.isSuccessful) {
                        when (response.body()?.status) {
                            "OK" -> {
                                jobPostListViewModel.initJobPostList(response.body()?.data!!)
                            }
                            "ERROR" -> {
                                // TODO
                            }
                        }
                    }
                }
            })
        }

        activity?.toolbar_title!!.text = "취뽀"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.more.setOnClickListener(View.OnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, HomeJobPostMoreFragment())
                addToBackStack("home")
            }?.commit()
        })

        view.plus.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, HomeJobPostMoreFragment())
                addToBackStack("home")
            }?.commit()
        }

    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    private fun getBannerImages(): Array<String> {
        val bannerImages = arrayOf(
            "https://board.jinhak.com/BoardV1/Upload/Job/TodayCatch/%EC%95%8C%ED%8C%8C%EC%82%AC%EC%9D%B4%EC%B8%A0%20%EB%A9%94%EC%9D%B8%20%EB%B0%B0%EB%84%88%20%EC%8B%9C%EC%95%88(%EC%88%98%EC%A0%95)_0813_14451.png",
            "https://board.jinhak.com/BoardV1/Upload/Job/TodayCatch/2021%ED%95%9C%EC%83%98%ED%82%A4%EC%B9%9C%EC%98%81%EC%97%85_0806_131056.jpg",
            "https://apple.contentsfeed.com/RealMedia/ads/Creatives/jobkorea/210721_seoul_mb/210720_seoul_752X110.jpg"
        )

        return bannerImages;
    }

    /* Opens companyLink of JobPost when RecyclerView item is clicked. */
    private fun adapterOnClick(jobPost: JobPost, view: View) {
        val url: String = jobPost.descriptionUrl
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        view.context.startActivity(intent)
    }

    private fun adapterStarOnClick(jobPost: JobPost, itemView: View) {
        // TODO: 로그인 토큰 NULL 혹은 NOT VALIDATION 일 때, dialog 띄우기

        // 로그인 되어있을시
        if (itemView.star.drawable.constantState == context?.resources?.getDrawable(R.drawable.ic_star_fill)?.constantState) {
            JobPostApi.instance(requireContext()).deleteBookmark(jobPost!!.id).enqueue(object :
                Callback<SpringServerResponse<Int>> {
                override fun onFailure(call: Call<SpringServerResponse<Int>>, t: Throwable) {
                    Log.d("retrofit fail", t.message)
                }

                override fun onResponse(
                    call: Call<SpringServerResponse<Int>>,
                    response: Response<SpringServerResponse<Int>>
                ) {
                    if (response.isSuccessful) {
                        itemView.star.setImageResource(R.drawable.ic_star_empty)
                        jobPostListViewModel.deleteBookmark(jobPost.id)
                        jobPostMoreListViewModel.deleteBookmark(jobPost.id)
                        likeJobPostListViewModel.deleteLikeJobPost(jobPost.id)
                    }
                }
            })
        } else {
            JobPostApi.instance(requireContext()).saveBookmark(jobPost!!.id).enqueue(object :
                Callback<SpringServerResponse<JobPost>> {
                override fun onFailure(call: Call<SpringServerResponse<JobPost>>, t: Throwable) {
                    Log.d("retrofit fail", t.message)
                }

                override fun onResponse(
                    call: Call<SpringServerResponse<JobPost>>,
                    response: Response<SpringServerResponse<JobPost>>
                ) {
                    if (response.isSuccessful) {
                        itemView.star.setImageResource(R.drawable.ic_star_fill)
                        jobPostListViewModel.saveBookmark(jobPost.id)
                        jobPostMoreListViewModel.saveBookmark(jobPost.id)
                        val jobPost = jobPostListViewModel.getJobPostForId(jobPost.id)
                        likeJobPostListViewModel.insertLikeJobPost(jobPost)
                    }
                }
            })
        }
    }
}
