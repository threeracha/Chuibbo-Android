package com.example.chuibbo_android.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.main_activity.*

class PreferencesFaqFragment : Fragment() {
    private lateinit var faqList: List<Faq>
    private lateinit var adapter: FaqAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.preferences_faq_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar!!.setTitle("자주 묻는 질문")

        val recyclerView = activity?.findViewById<RecyclerView>(R.id.recyclerview_faq)
        faqList = ArrayList()
        faqList = loadData()

        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(activity?.applicationContext)
        adapter = FaqAdapter(faqList)
        recyclerView!!.adapter = adapter
    }

    private fun loadData(): List<Faq> {
        val faqList = ArrayList<Faq>()

        val faqs = resources.getStringArray(R.array.faq_questions)
        val answers = resources.getStringArray(R.array.faq_answers)

        for (i in faqs.indices) {
            val faq= Faq().apply {
                question = faqs[i]
                answer = answers[i]
            }
            faqList.add(faq)
        }
        return faqList
    }
}