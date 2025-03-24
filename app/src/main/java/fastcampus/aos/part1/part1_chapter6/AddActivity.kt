package fastcampus.aos.part1.part1_chapter6

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.google.android.material.chip.Chip
import fastcampus.aos.part1.part1_chapter6.databinding.ActivityAddBinding
import fastcampus.aos.part1.part1_chapter6.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddBinding
    private var originWord: Word? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        binding.addButton.setOnClickListener {
            if (originWord == null) add() else edit()
        }
    }

    private fun initView() {
        val types = listOf( "명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사" )
        binding.typeChipGroup.apply {
            types.forEach { text ->
                addView(createChip(text))
            }
        }

        originWord = intent.getParcelableExtra<Word>("originWord")
        originWord?.let { word ->
            binding.textTextInputEditText.setText(word.text)
            binding.meanTextInputEditText.setText(word.mean)

            val selectedChip = binding.typeChipGroup.children.firstOrNull { (it as Chip).text == word.type } as? Chip
            selectedChip?.isChecked = true
        }
    }

    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }

    private fun add() {
        val text = binding.textTextInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val word = Word(text, mean, type)

        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.insert(word)
            setResult(RESULT_OK, Intent().putExtra("isUpdated", true))
            runOnUiThread { Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show() }
            finish()
        }.start()
    }

    private fun edit() {
        val text = binding.textTextInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val editWord = originWord?.copy(text, mean, type)

        Thread {
            editWord?.let { word ->
                AppDatabase.getInstance(this)?.wordDao()?.update(word)
            }
            setResult(RESULT_OK, Intent().putExtra("editWord", editWord))
            runOnUiThread { Toast.makeText(this, "수정을 완료했습니다.", Toast.LENGTH_SHORT).show() }
            finish()
        }.start()
    }
}