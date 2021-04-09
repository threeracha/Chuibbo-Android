package com.example.chuibbo_android.download

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.chuibbo_android.R
import com.example.chuibbo_android.correction.FaceCorrectionFragment
import kotlinx.android.synthetic.main.main_activity.*

class DownloadFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.download_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btn : ImageButton = ImageButton(activity?.applicationContext)
        btn.setImageResource(R.drawable.ic_download)
        val l3 =
                androidx.appcompat.widget.Toolbar.LayoutParams(
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        Toolbar.LayoutParams.WRAP_CONTENT
                )
        btn.setBackgroundColor(Color.WHITE)
        btn.layoutParams = l3
        btn.setBackground(null)
        btn.setOnClickListener {
            // TODO: 2021/04/09 이미지 로컬 갤러리 & 서버에 다운로드
        }
        activity?.toolbar!!.addView(btn)
        // This is how to set layout_gravity properties to Button
        // must be put this code after put button view on the activity
        (btn.layoutParams as androidx.appcompat.widget.Toolbar.LayoutParams)?.apply {
            this.gravity = Gravity.RIGHT
        }


    }
}