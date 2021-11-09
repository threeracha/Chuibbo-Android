package com.example.chuibbo_android.calendar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.main_activity.*

class CalendarFragment : Fragment(R.layout.calendar_fragment) {
    private val bookMarkListViewModel by viewModels<BookMarkListViewModel> {
        BookMarkListViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.calendar_fragment, container, false)
        activity?.toolbar_title!!.text = "취뽀 채용달력"
        activity?.back_button!!.visibility = View.VISIBLE

        val bookMarkAdapter = BookMarkAdapter{ bookMark -> adapterOnClick(bookMark, view) }
        val recyclerView: RecyclerView = view.findViewById(R.id.job_schedule_view)
        recyclerView.adapter = bookMarkAdapter

        bookMarkListViewModel.bookMarkLiveData.observe(viewLifecycleOwner, {
            it?.let {
                bookMarkAdapter.submitList(it as MutableList<BookMark>)
            }
        })

        return view
    }

    private fun adapterOnClick(bookMark: BookMark, view: View) {
        val url: String? = bookMark.jobPost?.logoUrl
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        view.context.startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
