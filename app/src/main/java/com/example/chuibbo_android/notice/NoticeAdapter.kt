package com.example.chuibbo_android.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import com.example.chuibbo_android.faq.ToggleAnimation

class NoticeAdapter(
    private val noticeList: List<Notice>
) : RecyclerView.Adapter<NoticeAdapter.MyViewHolder>() {
    class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(notice: Notice) {
            val tv_title = itemView.findViewById<TextView>(R.id.tv_list_title)
            val tv_notice = itemView.findViewById<TextView>(R.id.tv_contents)
            val btn_expand = itemView.findViewById<ImageButton>(R.id.btn_expand)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.layout_expand)

            tv_title.text = notice.title
            tv_notice.text = notice.content
            btn_expand.setOnClickListener {
                val show = toggleLayout(!notice.isExpanded, it, layoutExpand)
                notice.isExpanded = show
            }
        }

        private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
            // 2
            ToggleAnimation.toggleArrow(view, isExpanded)
            if (isExpanded) {
                ToggleAnimation.expand(layoutExpand)
            } else {
                ToggleAnimation.collapse(layoutExpand)
            }
            return isExpanded
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.preferences_faq_and_notices_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }
}
