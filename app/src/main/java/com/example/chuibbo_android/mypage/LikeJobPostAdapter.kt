package com.example.chuibbo_android.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import com.example.chuibbo_android.home.ImageLoader
import com.example.chuibbo_android.home.JobPost
import kotlinx.android.synthetic.main.mypage_like_job_posting.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

class LikeJobPostAdapter(private val onClick: (JobPost) -> Unit) :
    ListAdapter<JobPost, LikeJobPostAdapter.LikeJobPostViewHolder>(LikeJobPostDiffCallback) {

    /* ViewHolder for LikeJobPost, takes in the inflated view and the onClick behavior. */
    class LikeJobPostViewHolder(itemView: View, val onClick: (JobPost) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val companyName: TextView = itemView.company_name
        private val companyDesc: TextView = itemView.company_desc
        private val companyDeadline: TextView = itemView.company_deadline
        private val companyLogo: ImageView = itemView.company_logo
        private var currentLikeJobPost: JobPost? = null

        init {
            itemView.setOnClickListener {
                currentLikeJobPost?.let {
                    onClick(it)
                }
            }
            itemView.star.setOnClickListener {
                currentLikeJobPost?.let {
                    onClick(it)
                }
            }
        }

        /* Bind likeJobPost name and image. */
        fun bind(likeJobPost: JobPost) {
            currentLikeJobPost = likeJobPost

            companyName.text = likeJobPost.companyName
            companyDesc.text = likeJobPost.subject

            // TODO: day만 계산되고, month, year은 계산 안됨
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDateTime.parse(likeJobPost?.endDate.replace("T", " "), formatter)
            val period = Period.between(LocalDate.now(), date.toLocalDate())
            if (period.getDays() >= 1)
                companyDeadline.text = "D-" + period.getDays()
            else if (period.getDays() == 0)
                companyDeadline.text = "D-Day"
            else
                companyDeadline.text = "마감"

            if (likeJobPost.logoUrl != null) {

                CoroutineScope(Dispatchers.Main).launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        ImageLoader.loadImage(likeJobPost.logoUrl)
                    }
                    companyLogo.setImageBitmap(bitmap)
                }
            }
        }
    }

    /* Creates and inflates view and return LikeJobPostViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeJobPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mypage_like_job_posting, parent, false)
        return LikeJobPostViewHolder(view, onClick)
    }

    /* Gets current likeJobPost and uses it to bind view. */
    override fun onBindViewHolder(holder: LikeJobPostViewHolder, position: Int) {
        val likeJobPost = getItem(position)
        holder.bind(likeJobPost)
    }
}

object LikeJobPostDiffCallback : DiffUtil.ItemCallback<JobPost>() {
    override fun areItemsTheSame(oldItem: JobPost, newItem: JobPost): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: JobPost, newItem: JobPost): Boolean {
        return oldItem.id == newItem.id
    }
}
