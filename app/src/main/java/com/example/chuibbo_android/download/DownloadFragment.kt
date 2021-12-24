package com.example.chuibbo_android.download

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.utils.Common
import kotlinx.android.synthetic.main.download_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import java.text.SimpleDateFormat
import java.util.*


class DownloadFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar_title!!.text = "사진 저장"
        activity?.back_button!!.visibility = View.VISIBLE
        activity?.download_button!!.visibility = View.VISIBLE

        return inflater.inflate(R.layout.download_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val common = Common(this, this.requireActivity())
        val result = BitmapFactory.decodeFile(activity?.cacheDir?.toString()+"/result4.jpg")
        img_download?.setImageBitmap(result)

        activity?.download_button!!.setOnClickListener {
            context?.deleteFile("origin.jpg")
            context?.deleteFile("result.jpg")
            context?.deleteFile("result2.jpg")
            context?.deleteFile("result3.jpg")

            val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale("ko", "KR"))
            val fileName = "result" + dateFormat.format(Date(System.currentTimeMillis()))
            common.saveBitmapToGallery(result, fileName)
            Toast.makeText(context, "이미지 저장 성공", Toast.LENGTH_SHORT).show()

            // TODO: 2021/04/09 이미지 서버에 다운로드

            context?.deleteFile("result4.jpg")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.back_button!!.visibility = View.GONE
        activity?.download_button!!.visibility = View.GONE
    }
}
