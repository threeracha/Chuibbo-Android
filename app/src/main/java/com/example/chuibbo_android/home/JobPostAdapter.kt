package com.example.chuibbo_android.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.home_job_posting.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class JobPostAdapter(private val onClick: (JobPost) -> Unit) :
    ListAdapter<JobPost, JobPostAdapter.JobPostViewHolder>(JobPostDiffCallback) {

    /* ViewHolder for JobPost, takes in the inflated view and the onClick behavior. */
    class JobPostViewHolder(itemView: View, val onClick: (JobPost) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val companyName: TextView = itemView.company_name
        private val companyDesc: TextView = itemView.company_desc
        private val companyDeadline: TextView = itemView.company_deadline
        private val companyLogo: ImageView = itemView.company_logo
        private var currentJobPost: JobPost? = null

        init {
            itemView.setOnClickListener {
                currentJobPost?.let {
                    onClick(it)
                }
            }
            itemView.star.setOnClickListener {
                currentJobPost?.let {
                    onClick(it)
                }
            }
        }

        /* Bind jobPost name and image. */
        fun bind(jobPost: JobPost) {
            currentJobPost = jobPost

            companyName.text = jobPost.companyName

            if (jobPost.subject.length > 10) {
                companyDesc.text = jobPost.subject.slice(IntRange(0,9)) + "..."
            } else {
                companyDesc.text = jobPost.subject
            }

            companyDeadline.text = calculateDday(jobPost.endDate)

            if (jobPost.logoUrl != null) {

                CoroutineScope(Dispatchers.Main).launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        ImageLoader.loadImage(jobPost.logoUrl)
                    }
                    companyLogo.setImageBitmap(bitmap)
                }
            }
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

    /* Creates and inflates view and return JobPostViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_job_posting, parent, false)
        return JobPostViewHolder(view, onClick)
    }

    /* Gets current jobPost and uses it to bind view. */
    override fun onBindViewHolder(holder: JobPostViewHolder, position: Int) {
        val jobPost = getItem(position)
        holder.bind(jobPost)
    }
}

object JobPostDiffCallback : DiffUtil.ItemCallback<JobPost>() {
    override fun areItemsTheSame(oldItem: JobPost, newItem: JobPost): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: JobPost, newItem: JobPost): Boolean {
        return oldItem.id == newItem.id
    }
}
