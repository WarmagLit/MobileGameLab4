package com.tsu.mobilegamelab4.cases.dragndrop.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DragListener
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DifferenceCallback

/**
 * A @androidx.recyclerview.widget.RecyclerView adapter to show draggable items
 *
 * @param onDragStarted will provide the current draggable view value. String in this case
 * */
class KeysAdapter(private val onDragStarted: (String) -> Unit) : ListAdapter<String, KeysAdapter.WordsViewHolder>(
  DifferenceCallback()
) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
    return WordsViewHolder(view)
  }

  override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  fun removeItem(selectedWord: String) {
    val list = ArrayList(currentList)
    list.remove(selectedWord)
    submitList(list)
  }

  inner class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(word: String) = itemView.run {
      //findViewById<TextView>(R.id.tvWord).text = word

      setOnLongClickListener { view ->
        // when user is long clicking on a view, drag process will start
        val data = ClipData.newPlainText("", word)
        val shadowBuilder = View.DragShadowBuilder(view)
        view.startDragAndDrop(data, shadowBuilder, view, 0)
        onDragStarted(word)
        true
      }

      setOnDragListener(DragListener())
    }
  }
}