package com.example.chuibbo_android.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.JobPostApi
import com.example.chuibbo_android.api.response.SpringServerResponse
import com.example.chuibbo_android.home.*
import com.example.chuibbo_android.preferences.PreferencesFragment
import com.example.chuibbo_android.utils.SessionManager
import kotlinx.android.synthetic.main.home_job_posting.view.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.mypage_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    private val photoAlbumViewModel by viewModels<PhotoAlbumViewModel> {
        context?.let { PhotoAlbumViewModelFactory(it) }!!
    }

    private val likeJobPostListViewModel by viewModels<LikeJobPostListViewModel> {
        context?.let { LikeJobPostListViewModelFactory(it) }!!
    }

    private val jobPostListViewModel by viewModels<JobPostListViewModel> {
        context?.let { JobPostListViewModelFactory(it) }!!
    }

    private val jobPostMoreListViewModel by viewModels<JobPostMoreListViewModel> {
        context?.let { JobPostMoreListViewModelFactory(it) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.mypage_fragment, container, false)

        sessionManager = SessionManager(requireContext())

        val user_info = sessionManager.fetchUserInfo()

        view.user_name.text = "$user_info 님"
        view.user_name1.text = user_info
        view.user_name2.text = user_info

        // photoAlbum recyclerview
        val recyclerviewPhotoAlbum: RecyclerView = view.recyclerview_resume_photo
        recyclerviewPhotoAlbum.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val photoAlbumAdapter = PhotoAlbumAdapter { photoAlbum -> photoAlbumAdapterOnClick(photoAlbum, view) }

        recyclerviewPhotoAlbum.adapter = photoAlbumAdapter

        photoAlbumViewModel.photoAlbumsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                photoAlbumAdapter.submitList(it as MutableList<PhotoAlbum>)
            }

            view.resume_photo_count.text = photoAlbumViewModel.getSize().toString()
        })

        // likeJobPost recyclerview
        val recyclerviewLikeJobPost: RecyclerView = view.recyclerview_like_job_posting
        recyclerviewLikeJobPost.layoutManager = LinearLayoutManager(context)
        val likeJobPostAdapter = LikeJobPostAdapter ({ likeJobPost -> likeJobPostAdapterOnClick(likeJobPost, view) },
            { likeJobPost, itemView -> likeJobPostAdapterStarOnClick(likeJobPost, itemView)})

        recyclerviewLikeJobPost.adapter = likeJobPostAdapter

        likeJobPostListViewModel.likeJobPostsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                likeJobPostAdapter.submitList(it as MutableList<JobPost>)
            }

            view.like_job_posting_count.text = likeJobPostListViewModel.getSize().toString()
        })

        // TODO: 앨범 & 관심있는 채용공고 data null일 때, default 이미지 적용

        activity?.toolbar_title!!.text = "마이페이지"
        activity?.settings_button!!.visibility = View.VISIBLE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.settings_button!!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameLayout, PreferencesFragment())
                addToBackStack(null)
            }?.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.settings_button!!.visibility = View.GONE
    }

    /* Opens dialog when RecyclerView item is clicked. */
    private fun photoAlbumAdapterOnClick(photoAlbum: PhotoAlbum, view: View) {
        var myalbumDialog = MypageMyalbumDialogFragment()
        myalbumDialog.isCancelable = false // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기

        // 데이터 전달
        val args = Bundle()
        args.putString("image", photoAlbum.image)
        args.putString("dateAndDesc", photoAlbum.date.replace("T", " ") + "\n" + photoAlbum.faceShape + ", " + photoAlbum.hair + ", " + photoAlbum.suit)
        myalbumDialog.arguments = args
        activity?.let { it1 -> myalbumDialog.show(it1.supportFragmentManager, "CustomDialog") }
    }

    /* Opens companyLink of LikeJobPost when RecyclerView item is clicked. */
    private fun likeJobPostAdapterOnClick(likeJobPost: JobPost, view: View) {
        val url: String = likeJobPost.descriptionUrl
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        view.context.startActivity(intent)
    }

    private fun likeJobPostAdapterStarOnClick(likeJobPost: JobPost, itemView: View) {
        if (itemView.star.drawable.constantState == context?.resources?.getDrawable(R.drawable.ic_star_fill)?.constantState) {
            JobPostApi.instance(requireContext()).deleteBookmark(likeJobPost!!.id).enqueue(object :
                Callback<SpringServerResponse<Int>> {
                override fun onFailure(call: Call<SpringServerResponse<Int>>, t: Throwable) {
                    Log.d("retrofit fail", t.message)
                }

                override fun onResponse(
                    call: Call<SpringServerResponse<Int>>,
                    response: Response<SpringServerResponse<Int>>
                ) {
                    if (response.isSuccessful) {
                        itemView.star.setImageResource(R.drawable.ic_star_empty)
                        likeJobPostListViewModel.deleteLikeJobPost(likeJobPost.id)
                        jobPostListViewModel.deleteBookmark(likeJobPost.id)
                        jobPostMoreListViewModel.deleteBookmark(likeJobPost.id)
                    }
                }
            })
        }
    }
}
