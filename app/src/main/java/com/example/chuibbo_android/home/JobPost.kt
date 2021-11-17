package com.example.chuibbo_android.home

import com.google.gson.annotations.SerializedName

data class JobPost(
    @SerializedName("id") val id: Int,
    @SerializedName("logo_url") val logoUrl: String,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("description_url") val descriptionUrl: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("areas") val areas: List<Area>,
    @SerializedName("jobs") val jobs: List<Job>,
    @SerializedName("career_types") val careerTypes: List<CareerType>,
    @SerializedName("bookmark") var bookmark: Boolean
)

data class Area(
    @SerializedName("id") val id: Int,
    @SerializedName("area") val area: String
)

data class Job(
    @SerializedName("id") val id: Int,
    @SerializedName("job_type") val jobType: String
)

data class CareerType(
    @SerializedName("id") val id: Int,
    @SerializedName("career_type") val careerType: String
)
