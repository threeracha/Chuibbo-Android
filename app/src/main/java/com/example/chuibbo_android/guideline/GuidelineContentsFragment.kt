import android.annotation.SuppressLint
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
import com.example.chuibbo_android.R.id.btn_next
import com.example.chuibbo_android.camera.CameraFragment
import kotlinx.android.synthetic.main.guideline_fragment_contents.*
import kotlinx.android.synthetic.main.main_activity.*

class GuidelineContentsFragment : Fragment() {
    private lateinit var btn: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.guideline_fragment_contents, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            when (it.getInt(ARGS_PAGER_POSITION)) {
                0 -> {
                    img_guide1.setImageResource(R.drawable.guideline_first)
                    tv_title.text = "1. 선택한 헤어와 유사하게 연출해 주세요."
                    tv_subtitle.text = "올림머리를 선택하셨다면 머리를 묶어주세요"
                }
                1 -> {
                    img_guide1.setImageResource(R.drawable.guideline_second)
                    tv_title.text = "2. 단순한 배경 앞에서 찍어주세요."
                    tv_subtitle.text = "자신의 머리카락, 옷 색상 등과 겹치지 않는 배경이 좋습니다"
                }
                2 -> {
                    img_guide1.setImageResource(R.drawable.guideline_third)
                    tv_title.text = "3. 안경이나 악세서리류는 제거하고 찍어주세요."
                    tv_subtitle.text = "너무 큰 악세서리는 안돼요"
                }
                3 -> {
                    img_guide1.setImageResource(R.drawable.guideline_forth)
                    tv_title.text = "4. 목을 드러낼 수 있는 옷을 입고 찍어주세요."
                    tv_subtitle.text = "후드티나 목 위로 올라오는 옷은 피해주세요"

                    // this is for next button on the last page
                    // btn = ImageButton(context)
                    btn = activity?.toolbar!!.findViewById(R.id.btn_next)
                    if (btn != null) {
                        activity?.toolbar!!.removeView(btn)
                    }
                    btn.visibility = View.VISIBLE
                    btn.setImageResource(R.drawable.ic_arrow_right)
                    val l3 =
                        androidx.appcompat.widget.Toolbar.LayoutParams(
                            Toolbar.LayoutParams.WRAP_CONTENT,
                            Toolbar.LayoutParams.WRAP_CONTENT
                        )
                    btn.setBackgroundColor(Color.WHITE)
                    btn.layoutParams = l3
                    btn.setBackground(null)
                    btn.setOnClickListener {
                        val transaction = activity?.supportFragmentManager!!.beginTransaction()
                        transaction.replace(R.id.frameLayout, CameraFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
                    activity?.toolbar!!.addView(btn)
                    // This is how to set layout_gravity properties to Button
                    // must be put this code after put button view on the activity
                    (btn.layoutParams as androidx.appcompat.widget.Toolbar.LayoutParams)?.apply {
                        this.gravity = Gravity.RIGHT
                    }
                }
                else -> {
                }
            }
        }
    }

    companion object {
        private const val ARGS_PAGER_POSITION = "args_pager_position"

        @JvmStatic
        fun newIntent(position: Int): GuidelineContentsFragment {
            return GuidelineContentsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARGS_PAGER_POSITION, position)
                }
            }
        }
    }
}
