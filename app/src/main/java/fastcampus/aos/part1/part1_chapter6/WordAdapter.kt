package fastcampus.aos.part1.part1_chapter6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fastcampus.aos.part1.part1_chapter6.databinding.ItemWordBinding

class WordAdapter(
    val wordList: MutableList<Word>,
    private val itemClickListener: ItemClickListener? = null
): RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: WordViewHolder,
        position: Int
    ) {
        val word = wordList[position]
        holder.bind(word)

        holder.itemView.setOnClickListener { itemClickListener?.onClick(word) }
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    class WordViewHolder(private val binding: ItemWordBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.apply {
                textTextView.text = word.text
                meanTextView.text = word.mean
                typeChip.text = word.type
            }

        }
    }

    interface ItemClickListener {
        fun onClick(word: Word) {

        }
    }
}