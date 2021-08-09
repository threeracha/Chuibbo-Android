package com.example.chuibbo_android.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.home_fragment.view.recyclerview
import kotlinx.android.synthetic.main.home_job_posting_more_fragment.*
import kotlinx.android.synthetic.main.home_job_posting_more_fragment.view.*
import kotlinx.android.synthetic.main.main_activity.*

class HomeJobPostingMoreFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.home_job_posting_more_fragment, container, false)

        val career = arrayOf("신입", "경력")
        val job = arrayOf("기획/마케팅", "디자인", "IT")
        val area = arrayOf("서울", "경기", "인천", "강원", "충청", "전라", "경상", "제주")
        val sort = arrayOf("최신순", "마감순")

        val careerAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, career) }
        view.dropdown_career.adapter = careerAdapter
        val jobAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, job) }
        view.dropdown_job.adapter = jobAdapter
        val areaAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, area) }
        view.dropdown_area.adapter = areaAdapter
        val sortAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, sort) }
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
                    // ...
                    else -> {
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

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

        activity?.back_button!!.visibility = View.VISIBLE

        val recyclerView: RecyclerView = view.recyclerview
        val numberOfColumns = 2
        recyclerView.layoutManager = GridLayoutManager(context, numberOfColumns)
        var adapter = HomeJobPostingRecyclerViewAdapter(context, data)
        // adapter.setClickListener(this)
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar_title!!.text = "취뽀 채용공고"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
    }
}
