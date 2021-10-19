package com.example.chuibbo_android.home

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.LocalDateTime

/* Handles operations on jobPostsLiveData and holds details about it. */
class JobPostDataSource(resources: Resources) {
    private val initialJobPostList = listOf(
        JobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/",
            LocalDateTime.of(2021, 9, 30, 12, 0, 0),
            LocalDateTime.of(2021, 10, 20, 12, 0, 0)
        ),
        JobPost(
            2,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr",
            LocalDateTime.of(2021, 8, 30, 12, 0, 0),
            LocalDateTime.of(2021, 9, 10, 12, 0, 0)
        ),
        JobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr",
            LocalDateTime.of(2021, 8, 26, 12, 0, 0),
            LocalDateTime.of(2021, 8, 30, 12, 0, 0)
        ),
        JobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/",
            LocalDateTime.of(2021, 10, 1, 12, 0, 0),
            LocalDateTime.of(2021, 10, 11, 12, 0, 0)
        ),
        JobPost(
            2,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr",
            LocalDateTime.of(2021, 10, 14, 12, 0, 0),
            LocalDateTime.of(2021, 10, 19, 12, 0, 0)
        ),
        JobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr",
            LocalDateTime.of(2021, 10, 30, 12, 0, 0),
            LocalDateTime.of(2021, 11, 4, 12, 0, 0)
        ),
        JobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/",
            LocalDateTime.of(2021, 9, 30, 12, 0, 0),
            LocalDateTime.of(2021, 10, 8, 12, 0, 0)
        ),
        JobPost(
            2,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr",
            LocalDateTime.of(2021, 9, 10, 12, 0, 0),
            LocalDateTime.of(2021, 9, 20, 12, 0, 0)
        ),
        JobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr",
            LocalDateTime.of(2021, 9, 30, 12, 0, 0),
            LocalDateTime.of(2021, 10, 20, 12, 0, 0)
        ),
        JobPost(
            1,
            "롯데제과",
            "롯데제과 채용공고",
            3,
            "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
            "https://www.lotteconf.co.kr/",
            LocalDateTime.of(2021, 10, 3, 12, 0, 0),
            LocalDateTime.of(2021, 10, 20, 12, 0, 0)
        ),
        JobPost(
            2,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr",
            LocalDateTime.of(2021, 11, 15, 12, 0, 0),
            LocalDateTime.of(2021, 11, 20, 12, 0, 0)
        ),
        JobPost(
            3,
            "두산",
            "두산 채용",
            2,
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
            "https://www.doosan.com/kr",
            LocalDateTime.of(2021, 12, 30, 12, 0, 0),
            LocalDateTime.of(2021, 12, 20, 12, 0, 0)
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
