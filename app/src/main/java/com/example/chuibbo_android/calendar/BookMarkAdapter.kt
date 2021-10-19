package com.example.chuibbo_android.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import java.time.LocalDate
import java.time.Period

class BookMarkAdapter(private val onClick: (BookMark) -> Unit) :
    ListAdapter<BookMark, BookMarkAdapter.BookMarkViewHolder>(BookMarkDiffCallback){

    class BookMarkViewHolder(itemView: View, val onClick: (BookMark) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val bookMarkTitleTextView: TextView = itemView.findViewById(R.id.tv_job_recuiting_title)
        private val bookMarkDurationTextView: TextView = itemView.findViewById(R.id.tv_job_duration)
        private val bookMarkDdayTextView: TextView = itemView.findViewById(R.id.tv_dday)
        private var currentBookMark: BookMark? = null

        init {
            itemView.setOnClickListener {
                currentBookMark?.let {
                    onClick(it)
                }
            }
        }

        fun bind(bookMark: BookMark) {
            currentBookMark = bookMark

            bookMarkTitleTextView.text = currentBookMark!!.jobPost?.companyDesc
            bookMarkDurationTextView.text = currentBookMark!!.jobPost?.startDate.toString() + " ~ " + currentBookMark!!.jobPost?.endDate.toString()
            val period = Period.between(LocalDate.now(), currentBookMark!!.jobPost?.endDate?.toLocalDate())
            if (period.getDays() >= 1)
                bookMarkDdayTextView.text = "D-" + period.getDays()
            else if (period.getDays() == 0)
                bookMarkDdayTextView.text = "D-Day"
            else
                bookMarkDdayTextView.text = "마감"
            Log.d("haha", currentBookMark!!.id.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_item_view, parent, false)
        return BookMarkViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: BookMarkViewHolder, position: Int) {
        val bookMark = getItem(position)
        holder.bind(bookMark)
    }
}

object BookMarkDiffCallback : DiffUtil.ItemCallback<BookMark>() {
    override fun areItemsTheSame(oldItem: BookMark, newItem: BookMark): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BookMark, newItem: BookMark): Boolean {
        return oldItem.id == newItem.id
    }

}
