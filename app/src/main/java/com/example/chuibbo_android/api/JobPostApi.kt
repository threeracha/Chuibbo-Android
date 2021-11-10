package com.example.chuibbo_android.api

import com.example.chuibbo_android.api.response.SpringResponse2
import com.example.chuibbo_android.home.JobPost
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JobPostApi {

    @GET("/api/v1/job_posts")
    fun getJobPosts(): Call<SpringResponse2<List<JobPost>>>

    @GET("/api/v1/job_posts/more")
    fun getJobPostsMore(
        @Query("page") page: Int
    ): Call<SpringResponse2<List<JobPost>>>

    companion object {
        val instance = ApiGenerator().generateSpring2(JobPostApi::class.java)
    }
}
