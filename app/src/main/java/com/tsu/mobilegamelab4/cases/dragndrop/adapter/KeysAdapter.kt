package com.tsu.mobilegamelab4.cases.dragndrop.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DifferenceCallback
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DragListener
import com.tsu.mobilegamelab4.game.items.Key

/**
 * A @androidx.recyclerview.widget.RecyclerView adapter to show draggable items
 *
 * @param onDragStarted will provide the current draggable view value. String in this case
 * */
class KeysAdapter(private val onDragStarted: (String) -> Unit) : ListAdapter<String, KeysAdapter.KeysViewHolder>(
  DifferenceCallback()
) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeysViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_keyholder, parent, false)
    return KeysViewHolder(view)
  }

  override fun onBindViewHolder(holder: KeysViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  fun removeItem(selectedKey: String) {
    val list = ArrayList(currentList)
    list.remove(selectedKey)
    submitList(list)
  }

  inner class KeysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val key = itemView.findViewById<ImageView>(R.id.imageViewKey)
    val holder = itemView.findViewById<ImageView>(R.id.imageViewHolder)
    fun bind(keyType: String) = itemView.run {
      when(keyType) {
        "red" -> key.setImageResource(R.drawable.red_key)
        "green" -> key.setImageResource(R.drawable.green_key)
        "yellow" -> key.setImageResource(R.drawable.yellow_key)
        "blue" -> key.setImageResource(R.drawable.blue_key)
      }
      setOnLongClickListener { view ->
        // when user is long clicking on a view, drag process will start
        val data = ClipData.newPlainText("", keyType)
        val shadowBuilder = View.DragShadowBuilder(view)
        view.startDragAndDrop(data, shadowBuilder, view, 0)
        onDragStarted(keyType)
        true
      }

      setOnDragListener(DragListener())
    }
  }


  override fun getItemCount(): Int {
    return currentList.size
  }

}
