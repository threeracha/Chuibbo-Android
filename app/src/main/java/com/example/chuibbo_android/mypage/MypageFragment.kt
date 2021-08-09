package com.example.chuibbo_android.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        // dummy data to populate the RecyclerView with
        var albumData = ArrayList<AlbumModel>()
        albumData.add(
            AlbumModel(
                "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
                "2021.08.02", "긴머리, 정장1"
            )
        )
        albumData.add(
            AlbumModel(
                "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
                "2021.08.02", "긴머리, 정장1"
            )
        )
        albumData.add(
            AlbumModel(
                "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
                "2021.08.02", "긴머리, 정장1"
            )
        )
        albumData.add(
            AlbumModel(
                "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
                "2021.08.02", "긴머리, 정장1"
            )
        )

        // TODO: company_desc가 일정 길이 이상이면 자르기
        var likeData = ArrayList<LikeJobPostingModel>()
        likeData.add(
            LikeJobPostingModel(
                "롯데제과",
                "롯데제과 채용공고",
                3,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "https://www.lotteconf.co.kr/"
            )
        )
        likeData.add(
            LikeJobPostingModel(
                "두산",
                "두산 채용",
                2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
                "https://www.doosan.com/kr"
            )
        )
        likeData.add(
            LikeJobPostingModel(
                "두산",
                "두산 채용 소식2",
                1,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
                "https://www.doosan.com/kr"
            )
        )

        var view: View = inflater.inflate(R.layout.mypage_fragment, container, false)

        val recyclerview_resume_photo: RecyclerView = view.recyclerview_resume_photo
        recyclerview_resume_photo.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        var resume_photo_adapter = MypageAlbumRecyclerViewAdpater(context, albumData)
        // resume_photo_adapter.setClickListener(this)
        recyclerview_resume_photo.adapter = resume_photo_adapter

        val recyclerView_like_job_posting: RecyclerView = view.recyclerview_like_job_posting
        recyclerView_like_job_posting.layoutManager = LinearLayoutManager(context)
        var like_job_posting_adapter = MypageLikeJobPostingRecyclerViewAdapter(context, likeData)
        // adapter.setClickListener(this)
        recyclerView_like_job_posting.adapter = like_job_posting_adapter

        view.resume_photo_count.text = albumData.size.toString()
        view.like_job_posting_count.text = likeData.size.toString()

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

        /* TODO: 클릭시 다이얼로그
        view.image1.setOnClickListener{
            var myalbumDialog: MypageMyalbumDialogFragment = MypageMyalbumDialogFragment()
            myalbumDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기
            activity?.let { it1 -> myalbumDialog.show(it1.supportFragmentManager, "CustomDialog") }
        }
        */

        // TODO: star 클릭시 color 변화

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
