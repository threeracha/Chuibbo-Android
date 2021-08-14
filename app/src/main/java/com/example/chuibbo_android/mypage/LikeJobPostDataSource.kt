package com.example.chuibbo_android.mypage

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/* Handles operations on likeJobPostsLiveData and holds details about it. */
class LikeJobPostDataSource(resources: Resources) {
    private val initialLikeJobPostList = listOf(
        LikeJobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        LikeJobPost(
            2,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        LikeJobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
        LikeJobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        LikeJobPost(
            2,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        LikeJobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
        LikeJobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        LikeJobPost(
            2,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        LikeJobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
        LikeJobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        LikeJobPost(
            2,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        LikeJobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        )

    )

    private val likeJobPostsLiveData = MutableLiveData(initialLikeJobPostList)

    /* Adds likeJobPost to liveData and posts value. */
    fun addLikeJobPost(likeJobPost: LikeJobPost) {
        val currentList = likeJobPostsLiveData.value
        if (currentList == null) {
            likeJobPostsLiveData.postValue(listOf(likeJobPost))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, likeJobPost)
            likeJobPostsLiveData.postValue(updatedList)
        }
    }

    /* Removes likeJobPost from liveData and posts value. */
    fun removeJobPost(likeJobPost: LikeJobPost) {
        val currentList = likeJobPostsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(likeJobPost)
            likeJobPostsLiveData.postValue(updatedList)
        }
    }

    /* Returns likeJobPost given an ID. */
    fun getLikeJobPostForId(id: Int): LikeJobPost? {
        likeJobPostsLiveData.value?.let { likeJobPosts ->
            return likeJobPosts.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getLikeJobPostList(): LiveData<List<LikeJobPost>> {
        return likeJobPostsLiveData
    }

    fun getLikeJobPostSize(): Int {
        return likeJobPostsLiveData.value?.toMutableList()!!.size
    }

    companion object {
        private var INSTANCE: LikeJobPostDataSource? = null

        fun getDataSource(resources: Resources): LikeJobPostDataSource {
            return synchronized(LikeJobPostDataSource::class) {
                val newInstance = INSTANCE ?: LikeJobPostDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
