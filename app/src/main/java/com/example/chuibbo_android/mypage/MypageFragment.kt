package com.example.chuibbo_android.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.chuibbo_android.R
import com.example.chuibbo_android.login.LoginFragment
import com.example.chuibbo_android.preferences.PreferencesFragment
import kotlinx.android.synthetic.main.mypage_fragment.view.*


class MypageFragment : Fragment() {

    private lateinit var adapter: AlbumAdapter
    private lateinit var models: ArrayList<AlbumModel>
    private var dotscount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.mypage_fragment, container, false)

        models = ArrayList()
        models.add(AlbumModel(R.drawable.guideline_first,"Dobby", "Kutta"))
        models.add(AlbumModel(R.drawable.guideline_second,"Kitto", "billi"))
        models.add(AlbumModel(R.drawable.guideline_third,"Cozmo", "Lambardor"))

        adapter = context?.let { AlbumAdapter(models, it) }!!
        view.view_pager.adapter = adapter



        view.view_pager.setPadding(60, 0, 60, 0)
        dotscount = adapter.count

        val dots = arrayOfNulls<ImageView>(dotscount)

        for (i in 0 until dotscount) {
            dots[i] = ImageView(context)
            dots[i]!!.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_settings_24))
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(8, 0, 8, 0)
        }
        dots[0]?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_settings_24))

        view.view_pager.setOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int){}

            override fun onPageSelected(position: Int) {
                for (i in 0 until dotscount) {
                    dots[i]?.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.ic_baseline_settings_24))
                }
                dots[position]?.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.ic_baseline_settings_24))
            }
        })


        view.login_button.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, LoginFragment())
                addToBackStack(null)
            }?.commit()
        }

        view.settings_button.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesFragment())
                addToBackStack(null)
            }?.commit()
        }

        // TODO: 사진 클릭시 Dialog
        var myalbumDialog: MypageMyalbumDialogFragment = MypageMyalbumDialogFragment()
        myalbumDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기
        view.view_pager?.setOnClickListener {
            activity?.supportFragmentManager?.let { it -> myalbumDialog.show(it, "My album") }
        }

        return view
    }
}