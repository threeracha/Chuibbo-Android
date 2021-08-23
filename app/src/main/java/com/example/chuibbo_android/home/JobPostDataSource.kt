package com.example.chuibbo_android.home

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/* Handles operations on jobPostsLiveData and holds details about it. */
class JobPostDataSource(resources: Resources) {
    private val initialJobPostList = listOf(
        JobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        JobPost(
            2,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
        JobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
        JobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        JobPost(
            2,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
        JobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
        JobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        JobPost(
            2,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
        JobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
        JobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/"
        ),
        JobPost(
            2,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
        JobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr"
        ),
    )

    private val jobPostsLiveData = MutableLiveData(initialJobPostList)

    /* Adds jobPost to liveData and posts value. */
    fun addJobPost(jobPost: JobPost) {
        val currentList = jobPostsLiveData.value
        if (currentList == null) {
            jobPostsLiveData.postValue(listOf(jobPost))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, jobPost)
            jobPostsLiveData.postValue(updatedList)
        }
    }

    /* Removes jobPost from liveData and posts value. */
    fun removeJobPost(jobPost: JobPost) {
        val currentList = jobPostsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(jobPost)
            jobPostsLiveData.postValue(updatedList)
        }
    }

    /* Returns jobPost given an ID. */
    fun getJobPostForId(id: Int): JobPost? {
        jobPostsLiveData.value?.let { jobPosts ->
            return jobPosts.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getJobPostList(): LiveData<List<JobPost>> {
        return jobPostsLiveData
    }

    companion object {
        private var INSTANCE: JobPostDataSource? = null

        fun getDataSource(resources: Resources): JobPostDataSource {
            return synchronized(JobPostDataSource::class) {
                val newInstance = INSTANCE ?: JobPostDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
