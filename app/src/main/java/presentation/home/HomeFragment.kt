package presentation.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tome_aos.R
import com.example.tome_aos.databinding.FragmentHomeBinding
import presentation.dialog.MissionClass
import presentation.dialog.SnackDialog


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var isFabOpen = false // Fab 버튼 default는 닫혀있음

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setFABClickEvent()
        setSnackClickEvent()
    }

    private fun setSnackClickEvent(){
        binding.snackBtn.setOnClickListener {
            val dialog = SnackDialog()
            dialog.show(activity?.supportFragmentManager!!, "SnackDialog")
        }
    }

    private fun setFABClickEvent() {
        // 플로팅 버튼 클릭시 애니메이션 동작 기능
        binding.fabMain.setOnClickListener {
            toggleFab()
        }

        if (binding.fabBookAlign.translationY == 0f) {
            binding.fabBookAlign.visibility = View.GONE
        }

        if (binding.fabStore.translationY == 0f) {
            binding.fabStore.visibility = View.GONE
        }

        // 플로팅 버튼 클릭 이벤트 - 도감
        binding.fabBookAlign.setOnClickListener {
            Toast.makeText(this.context, "도감 버튼 클릭!", Toast.LENGTH_SHORT).show()
        }

        // 플로팅 버튼 클릭 이벤트 - 상점
        binding.fabStore.setOnClickListener {
            Toast.makeText(this.context, "상점 버튼 클릭!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleFab() {
        // 플로팅 액션 버튼 닫기
        if (isFabOpen) {
            val animation1 = AlphaAnimation(1f, 0f)
            animation1.duration = 300
            animation1.fillAfter = true
            binding.fabStore.startAnimation(animation1)
            binding.fabBookAlign.startAnimation(animation1)

            binding.fabMain.isSelected = false
            ObjectAnimator.ofFloat(binding.fabBookAlign, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabStore, "translationY", 0f).apply { start() }

        } else { // 플로팅 액션 버튼 열기
            val animation2 = AlphaAnimation(0f, 1f)
            animation2.duration = 100
            animation2.fillAfter = true
            binding.fabStore.startAnimation(animation2)
            binding.fabBookAlign.startAnimation(animation2)

            binding.fabMain.isSelected = true
            ObjectAnimator.ofFloat(binding.fabBookAlign, "translationY", 50f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabStore, "translationY", 100f).apply { start() }
            binding.fabBookAlign.visibility = View.VISIBLE
            binding.fabStore.visibility = View.VISIBLE
        }
        isFabOpen = !isFabOpen
    }
}
