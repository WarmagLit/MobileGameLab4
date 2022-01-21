package com.tsu.mobilegamelab4.cases.dragndrop.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DragListener
import com.tsu.mobilegamelab4.cases.inventory.Key

/**
 * A @androidx.recyclerview.widget.RecyclerView adapter to show draggable items
 *
 * @param onDragStarted will provide the current draggable view value. String in this case
 * */
class KeysAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var keys: MutableList<Key> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeysViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_keyholder, parent, false)
    return KeysViewHolder(view)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val viewHolder = holder as KeysViewHolder
    viewHolder.bind()
  }

  @SuppressLint("NotifyDataSetChanged")
  fun removeItem(selectedKey: Key) {
    keys.remove(selectedKey)
    notifyDataSetChanged()
  }

  inner class KeysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind() = itemView.run {

      setOnLongClickListener { view ->
        // when user is long clicking on a view, drag process will start
        val data = ClipData.newPlainText("", "")
        val shadowBuilder = View.DragShadowBuilder(view)
        view.startDragAndDrop(data, shadowBuilder, view, 0)
        //onDragStarted("")
        true
      }

      setOnDragListener(DragListener())
    }
  }


  override fun getItemCount(): Int {
    return keys.size
  }

  @SuppressLint("NotifyDataSetChanged")
  fun setKeysAmount(count: Int) {
    keys.clear()
    for (i in 1..count) {
      val key = Key()
      keys.add(key)
    }
    notifyDataSetChanged()
  }
}
