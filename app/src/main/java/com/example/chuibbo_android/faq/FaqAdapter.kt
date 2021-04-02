package com.example.chuibbo_android.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.preferences_faq_and_notices_item.view.*

class FaqAdapter(
    private val faqList: List<Faq>
) : RecyclerView.Adapter<FaqAdapter.MyViewHolder>() {
    class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(faq: Faq) {
            val tv_title = itemView.findViewById<TextView>(R.id.tv_list_title)
            val tv_answer = itemView.findViewById<TextView>(R.id.tv_contents)
            val btn_expand = itemView.findViewById<ImageButton>(R.id.btn_expand)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.layout_expand)

            tv_title.text = faq.question
            tv_answer.text = faq.answer
            btn_expand.setOnClickListener {
                val show = toggleLayout(!faq.isExpanded, it, layoutExpand)
                faq.isExpanded = show
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
        holder.bind(faqList[position])
    }

    override fun getItemCount(): Int {
        return faqList.size
    }


}