package com.example.chuibbo_android.option

import androidx.lifecycle.LiveData
import com.example.chuibbo_android.camera.FaceType
import com.example.chuibbo_android.camera.Hairstyle
import com.example.chuibbo_android.camera.Sex

data class Option(
    var id: String, // 유저 아이디
    var sex: Sex,
    var face_shape: FaceType,
    var hairstyle: Hairstyle,
    var prev_hairstyle: Hairstyle,
    var suit: Int
) {
    // 싱글턴
    object Opt {
        val opt = Option("threeracha", Sex.FEMALE, FaceType.ROUND, Hairstyle.MID, Hairstyle.MID, 0)
    }
}
