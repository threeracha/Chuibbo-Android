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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

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

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDateTime.parse(jobPost?.endDate.replace("T", " "), formatter)
            val period = Period.between(LocalDate.now(), date.toLocalDate())
            if (period.getDays() >= 1)
                companyDeadline.text = "D-" + period.getDays()
            else if (period.getDays() == 0)
                companyDeadline.text = "D-Day"
            else
                companyDeadline.text = "마감"

            if (jobPost.logoUrl != null) {

                CoroutineScope(Dispatchers.Main).launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        ImageLoader.loadImage(jobPost.logoUrl)
                    }
                    companyLogo.setImageBitmap(bitmap)
                }
            }
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
