package com.example.chuibbo_android.correction

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.api.ApiGenerator
import com.example.chuibbo_android.api.MakeupApi
import com.example.chuibbo_android.api.request.MakeupRequest
import com.example.chuibbo_android.api.request.MakeupStrongRequest
import com.example.chuibbo_android.utils.Common
import kotlinx.android.synthetic.main.face_correction_fragment.*
import kotlinx.android.synthetic.main.face_correction_makeup_fragment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.util.*

class FaceCorrectionMakeupFragment : Fragment() {
    private val makeupService = MakeupApi.instance
    private var flagLip = false
    private var flagCheek = false
    private var flagEyebrow = false
    private var rColor = 247
    private var gColor = 40
    private var bColor = 57
    private var size = 33
    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.face_correction_makeup_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 메이크업 강도 조절 바
        activity?.seekbar_makeup?.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                uploadStrong(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                makeupFace(rColor, gColor, bColor, size, index)
            }
        })

        img_lipstick?.setOnClickListener {
            when(flagLip) {
                true ->
                    activity?.seekbar_makeup?.visibility = View.INVISIBLE
                false -> {
                    activity?.seekbar_makeup?.visibility = View.VISIBLE
                    index = 2
                    uploadParameter(rColor, gColor, bColor, size, index)
                }
            }
            // TODO: 다양한 컬러 사용자로부터 선택할 수 있도록 하는 기능 추가
        }
        img_cheek?.setOnClickListener {
            when(flagCheek) {
                true ->
                    activity?.seekbar_makeup?.visibility = View.INVISIBLE
                false -> {
                    activity?.seekbar_makeup?.visibility = View.VISIBLE
                    index = 3
                    uploadParameter(rColor, gColor, bColor, size, index)
                }
            }
            // TODO: 다양한 컬러 사용자로부터 선택할 수 있도록 하는 기능 추가
            index = 3
            uploadParameter(rColor, gColor, bColor, size, index)
        }
        img_eyebrow?.setOnClickListener {
            when(flagEyebrow) {
                true ->
                    activity?.seekbar_makeup?.visibility = View.INVISIBLE
                false -> {
                    activity?.seekbar_makeup?.visibility = View.VISIBLE
                    // TODO: 눈썹 기능 추가
                    //uploadParameter(247, 40, 57, 100, 1)
                }
            }
        }
    }

    // 파라미터 전달
    private fun uploadParameter(r:Int, g:Int, b:Int, size:Int, index:Int){
        val input = HashMap<String, Int>()
        input["rColor"] = r
        input["gColor"] = g
        input["bColor"] = b
        input["size"] = size
        input["index"] = index
        makeupService.uploadParameter(input).enqueue(object : Callback<MakeupRequest>{
            override fun onResponse(call: Call<MakeupRequest>, response: Response<MakeupRequest>) {
                downloadFile()
            }

            override fun onFailure(call: Call<MakeupRequest>, t: Throwable) {
            }
        })
    }

    //서버로 이미지 다운로드
    private fun downloadFile() {
        val common = Common(this, this.requireActivity())
        makeupService.downloadFile(ApiGenerator.HOST+"/static/Output.jpg/")
            .enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val inputS : InputStream = response.body()!!.byteStream()
                    val bmp : Bitmap = BitmapFactory.decodeStream(inputS)
                    activity?.img_face_correction?.setImageBitmap(bmp)

                    val fileName = "result"
                    common.saveBitmapToJpeg(bmp, fileName)

                    Toast.makeText(context, "메이크업에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    //메이크업 강도 전달
    private fun uploadStrong(strong: Int) {
        makeupService.uploadStrong(strong).enqueue(object : Callback<MakeupStrongRequest>{
            override fun onResponse(call: Call<MakeupStrongRequest>, response: Response<MakeupStrongRequest>) {
                downloadFile()
            }

            override fun onFailure(call: Call<MakeupStrongRequest>, t: Throwable) {
            }
        })
    }

    //메이크업 요청
    private fun makeupFace(r:Int, g:Int, b:Int, size:Int, index:Int) {
        val strong = 1
        val input = HashMap<String, Int>()
        input["rColor"] = r
        input["gColor"] = g
        input["bColor"] = b
        input["size"] = size
        input["index"] = index
        makeupService.makeUpFace(input).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                downloadFile()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("makeup failed",t.message)
                Toast.makeText(context, "makeupFaceFailed"+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }
}
