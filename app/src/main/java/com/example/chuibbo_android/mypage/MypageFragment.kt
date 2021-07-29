package com.example.chuibbo_android.mypage

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.login.LoginFragment
import com.example.chuibbo_android.preferences.PreferencesFragment
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.mypage_fragment.view.*

class MypageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.mypage_fragment, container, false)

        // 마이페이지 초기 화면

        view.login_button.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, LoginFragment())
                addToBackStack(null)
            }?.commit()
        }

        activity?.settings_button!!.visibility = View.VISIBLE
        activity?.settings_button!!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesFragment())
                addToBackStack(null)
            }?.commit()
        }

        // xml로 적용 안되어, 동적으로 corner round 적용
        var drawable: GradientDrawable = context?.getDrawable(R.drawable.round_10) as GradientDrawable

        view.image1.background = drawable
        view.image1.clipToOutline = true
        view.image2.background = drawable
        view.image2.clipToOutline = true
        view.image3.background = drawable
        view.image3.clipToOutline = true
        view.image4.background = drawable
        view.image4.clipToOutline = true

        view.image1.setOnClickListener{
            var myalbumDialog: MypageMyalbumDialogFragment = MypageMyalbumDialogFragment()
            myalbumDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기
            activity?.let { it1 -> myalbumDialog.show(it1.supportFragmentManager, "CustomDialog") }
        }

        view.star.setOnClickListener{
            if (view.star.resources.equals(R.drawable.star_empty)) // TODO: 추후에 좋아요 기능 구현
                view.star.setImageResource(R.drawable.star_fill)
            else
                view.star.setImageResource(R.drawable.star_empty)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar_title!!.text = "마이페이지"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.settings_button!!.visibility = View.GONE
    }
}
