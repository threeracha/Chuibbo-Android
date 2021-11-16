package com.example.chuibbo_android.mypage

import com.google.gson.annotations.SerializedName

data class PhotoAlbum(
    val id: Int,
    @SerializedName("photo_url") val image: String,
    @SerializedName("created_at") val date: String,
    @SerializedName("option_face_shape") val faceShape: String,
    @SerializedName("option_hair") val hair: String,
    @SerializedName("option_suit") val suit: String,
)
