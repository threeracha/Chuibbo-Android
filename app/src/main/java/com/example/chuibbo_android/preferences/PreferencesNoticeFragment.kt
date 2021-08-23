package com.example.chuibbo_android.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import com.example.chuibbo_android.notice.Notice
import com.example.chuibbo_android.notice.NoticeAdapter
import kotlinx.android.synthetic.main.main_activity.*

class PreferencesNoticeFragment : Fragment() {
    private lateinit var noticeList: List<Notice>
    private lateinit var adapter: NoticeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar_title!!.text = "공지사항"
        activity?.back_button!!.visibility = View.VISIBLE

        return inflater.inflate(R.layout.preferences_notice_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = activity?.findViewById<RecyclerView>(R.id.recyclerview_notice)
        noticeList = ArrayList()
        noticeList = loadData()

        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(activity?.applicationContext)
        adapter = NoticeAdapter(noticeList)
        recyclerView!!.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
    }

    private fun loadData(): List<Notice> {
        val noticeList = ArrayList<Notice>()

        val titles = resources.getStringArray(R.array.notice_title)
        val contents = resources.getStringArray(R.array.notice_content)

        for (i in titles.indices) {
            val notice = Notice().apply {
                title = titles[i]
                content = contents[i]
            }
            noticeList.add(notice)
        }
        return noticeList
    }
}
