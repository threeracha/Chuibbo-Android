package com.example.chuibbo_android.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.login.LoginFragment
import com.example.chuibbo_android.preferences.PreferencesFragment
import kotlinx.android.synthetic.main.mypage_fragment.view.*


class MypageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.mypage_fragment, container, false)

        // 마이페이지 초기 화면
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.album, MypageVerticalFragment())
        }?.commit()

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

        view.vertical_album.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.album, MypageVerticalFragment())
            }?.commit()
            view.vertical_album.setImageResource(R.drawable.card_vertical_colored)
            view.horizontal_album.setImageResource(R.drawable.card_horizontal)
        }

        view.horizontal_album.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.album, MypageHorizontalFragment())
            }?.commit()
            view.horizontal_album.setImageResource(R.drawable.card_horizontal_colored)
            view.vertical_album.setImageResource(R.drawable.card_vertical)
        }

        // TODO: 사진 클릭시 Dialog
        var myalbumDialog: MypageMyalbumDialogFragment = MypageMyalbumDialogFragment()
        myalbumDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기
//        view.view_pager?.setOnClickListener {
//            activity?.supportFragmentManager?.let { it -> myalbumDialog.show(it, "My album") }
//        }

        return view
    }
}