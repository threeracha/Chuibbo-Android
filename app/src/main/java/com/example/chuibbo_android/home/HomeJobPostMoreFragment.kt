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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.JobPostApi
import com.example.chuibbo_android.api.response.SpringResponse2
import kotlinx.android.synthetic.main.home_fragment.view.recyclerview
import kotlinx.android.synthetic.main.home_job_posting.view.*
import kotlinx.android.synthetic.main.home_job_posting_more_fragment.view.*
import kotlinx.android.synthetic.main.main_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeJobPostMoreFragment : Fragment() {

    private val jobPostListViewModel by viewModels<JobPostListViewModel> {
        context?.let { JobPostListViewModelFactory(it) }!!
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

        val recyclerView: RecyclerView = view.recyclerview
        val numberOfColumns = 2
        recyclerView.layoutManager = GridLayoutManager(context, numberOfColumns)
        recyclerView.adapter = jobPostAdapter

        jobPostListViewModel.jobPostsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                jobPostAdapter.submitList(it as MutableList<JobPost>)
            }
        })

        activity?.toolbar_title!!.text = "취뽀 채용공고"
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
                Callback<SpringResponse2<String>> {
                override fun onFailure(call: Call<SpringResponse2<String>>, t: Throwable) {
                    Log.d("retrofit fail", t.message)
                }

                override fun onResponse(
                    call: Call<SpringResponse2<String>>,
                    response: Response<SpringResponse2<String>>
                ) {
                    if (response.isSuccessful) {
                        itemView.star.setImageResource(R.drawable.ic_star_empty)
                        jobPostListViewModel.deleteBookmark(jobPost.id)
                    }
                }
            })
        } else {
            JobPostApi.instance(requireContext()).saveBookmark(jobPost!!.id).enqueue(object :
                Callback<SpringResponse2<String>> {
                override fun onFailure(call: Call<SpringResponse2<String>>, t: Throwable) {
                    Log.d("retrofit fail", t.message)
                }

                override fun onResponse(
                    call: Call<SpringResponse2<String>>,
                    response: Response<SpringResponse2<String>>
                ) {
                    if (response.isSuccessful) {
                        itemView.star.setImageResource(R.drawable.ic_star_fill)
                        jobPostListViewModel.saveBookmark(jobPost.id)
                    }
                }
            })
        }
    }
}
