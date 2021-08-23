package com.example.chuibbo_android.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import kotlinx.android.synthetic.main.home_fragment.view.recyclerview
import kotlinx.android.synthetic.main.home_job_posting_more_fragment.view.*
import kotlinx.android.synthetic.main.main_activity.*

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
        val url: String = jobPost.companyLink
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        view.context.startActivity(intent)
    }
}
