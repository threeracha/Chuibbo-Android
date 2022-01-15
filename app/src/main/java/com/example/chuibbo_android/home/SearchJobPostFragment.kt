package com.example.chuibbo_android.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.JobPostApi
import com.example.chuibbo_android.api.response.SpringServerResponse
import com.example.chuibbo_android.mypage.LikeJobPostListViewModel
import com.example.chuibbo_android.mypage.LikeJobPostListViewModelFactory
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.home_job_posting.view.*
import kotlinx.android.synthetic.main.home_job_posting_more_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchJobPostFragment : Fragment() {
    private var page = 1

    private val jobPostMoreListViewModel by viewModels<JobPostMoreListViewModel> {
        context?.let { JobPostMoreListViewModelFactory(it) }!!
    }

    private val jobPostListViewModel by viewModels<JobPostListViewModel> {
        context?.let { JobPostListViewModelFactory(it) }!!
    }

    private val likeJobPostListViewModel by viewModels<LikeJobPostListViewModel> {
        context?.let { LikeJobPostListViewModelFactory(it) }!!
    }

    private val searchJobPostListViewModel by viewModels<SearchJobPostListViewModel> {
        context?.let { SearchJobPostListViewModelFactory(it) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.home_job_posting_more_fragment, container, false)

        // TODO: enum class
        // dropdown setting
        val career = arrayOf("신입", "경력")
        val job = arrayOf("기획/마케팅", "디자인", "IT")
        val area = arrayOf("서울", "경기", "인천", "강원", "충청", "전라", "경상", "제주")
        val sort = arrayOf("최신순", "마감순")

        val careerAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, career) }
        view.dropdown_career.adapter = careerAdapter
        val jobAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, job) }
        view.dropdown_job.adapter = jobAdapter
        val areaAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, area) }
        view.dropdown_area.adapter = areaAdapter
        val sortAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, sort) }
        view.dropdown_sort.adapter = sortAdapter

        view.dropdown_career.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    //...
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val jobPostAdapter = JobPostAdapter ({ jobPost -> adapterOnClick(jobPost, view) }, { jobPost, itemView -> adapterStarOnClick(jobPost, itemView) })

        val recyclerView: RecyclerView = view.recyclerview_more
        val numberOfColumns = 2
        recyclerView.layoutManager = GridLayoutManager(context, numberOfColumns)
        recyclerView.adapter = jobPostAdapter

        searchJobPostListViewModel.searchJobPostLiveData.observe(viewLifecycleOwner, {
            it?.let {
                jobPostAdapter.submitList(it as MutableList<JobPost>)
            }
        })

        view.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                JobPostApi.instance(requireContext()).getSearchJobPosts(query!!).enqueue(object :
                    Callback<SpringServerResponse<List<JobPost>>> {
                    override fun onFailure(call: Call<SpringServerResponse<List<JobPost>>>, t: Throwable) {
                        Log.d("retrofit fail22", t.message)
                    }

                    override fun onResponse(
                        call: Call<SpringServerResponse<List<JobPost>>>,
                        response: Response<SpringServerResponse<List<JobPost>>>
                    ) {
                        if (response.isSuccessful) {
                            when (response.body()?.status) {
                                "OK" -> {
                                    searchJobPostListViewModel.initSearchJobPostList(response.body()?.data!!)
                                }
                                "ERROR" -> {
                                    // TODO
                                }
                            }
                        }
                    }
                })
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        activity?.toolbar_title!!.text = "취뽀 채용공고 검색"
        activity?.back_button!!.visibility = View.VISIBLE

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
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
                        searchJobPostListViewModel.deleteBookmark(jobPost.id)
                        jobPostMoreListViewModel.deleteBookmark(jobPost.id)
                        jobPostListViewModel.deleteBookmark(jobPost.id)
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
                        searchJobPostListViewModel.saveBookmark(jobPost.id)
                        jobPostMoreListViewModel.saveBookmark(jobPost.id)
                        jobPostListViewModel.saveBookmark(jobPost.id)
                        val jobPost = jobPostMoreListViewModel.getJobPostMoreForId(jobPost.id)
                        likeJobPostListViewModel.insertLikeJobPost(jobPost)
                    }
                }
            })
        }
    }
}
