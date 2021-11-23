package com.example.chuibbo_android.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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

            bookMarkTitleTextView.text = currentBookMark!!.jobPost?.subject
            bookMarkDurationTextView.text = currentBookMark!!.jobPost?.startDate.toString().replace("T", " ") + " ~ " +
                currentBookMark!!.jobPost?.endDate.toString().replace("T", " ")
            bookMarkDdayTextView.text = calculateDday(currentBookMark!!.jobPost?.endDate.toString().replace("T", " "))
        }

        private fun calculateDday(end: String): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val endDateTime = LocalDateTime.parse(end.replace("T", " "), formatter)
            val todayDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
            var difference = ChronoUnit.DAYS.between(todayDateTime.toLocalDate(), endDateTime.toLocalDate()).toInt() // 날짜만 계산

            if (endDateTime.isAfter(todayDateTime)) {
                if (endDateTime.year == todayDateTime.year && endDateTime.monthValue == todayDateTime.monthValue
                    && endDateTime.dayOfMonth == todayDateTime.dayOfMonth) {
                    return "D-Day"
                } else {
                    return "D-$difference"
                }
            } else
                return "마감"
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
