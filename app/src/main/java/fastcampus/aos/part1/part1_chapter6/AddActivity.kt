package fastcampus.aos.part1.part1_chapter6

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.chip.Chip
import fastcampus.aos.part1.part1_chapter6.databinding.ActivityAddBinding
import fastcampus.aos.part1.part1_chapter6.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val types = listOf( "명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사" )
        binding.typeChipGroup.apply {
            types.forEach { text ->
                addView(createChip(text))
            }
        }
    }

    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }
}