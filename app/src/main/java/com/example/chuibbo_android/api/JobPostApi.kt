package com.example.chuibbo_android.api

import android.content.Context
import com.example.chuibbo_android.api.response.SpringServerResponse
import com.example.chuibbo_android.home.JobPost
import retrofit2.Call
import retrofit2.http.*

interface JobPostApi {

    @GET("/api/v1/job-posts")
    fun getJobPosts(): Call<SpringServerResponse<List<JobPost>>>

    @GET("/api/v1/job-posts/more")
    fun getJobPostsMore(
        @Query("page") page: Int
    ): Call<SpringServerResponse<List<JobPost>>>

    @GET("/api/v1/job-posts/bookmarks")
    fun getBookmarks(): Call<SpringServerResponse<List<JobPost>>>

    @POST("/api/v1/job-posts/{job_post_id}/bookmarks")
    fun saveBookmark(
        @Path("job_post_id") id: Int
    ): Call<SpringServerResponse<JobPost>>

    @DELETE("/api/v1/job-posts/{job_post_id}/bookmarks")
    fun deleteBookmark(
        @Path("job_post_id") id: Int
    ): Call<SpringServerResponse<Int>>

    @GET("/api/v1/job-posts/search")
    fun getSearchJobPosts(
        @Query("query") query: String
    ): Call<SpringServerResponse<List<JobPost>>>

    companion object {
        val instance = ApiGenerator().generateSpring2(JobPostApi::class.java)

        fun instance(context: Context): JobPostApi {
            return ApiGenerator().generateSpring(JobPostApi::class.java, context)
        }
    }
}
