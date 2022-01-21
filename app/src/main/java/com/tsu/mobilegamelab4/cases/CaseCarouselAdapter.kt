package com.tsu.mobilegamelab4.cases

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.cases.dragndrop.adapter.KeysAdapter
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DifferenceCallback
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DragListener
import com.tsu.mobilegamelab4.cases.inventory.Case

class CasesAdapter(private val context: Context) : ListAdapter<String, CasesAdapter.MyViewHolder>(
    DifferenceCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.tvName.text = String.format("Row number  %d ", position)

        //holder.imgBanner.setImageResource(R.drawable.ic_chest)
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgBanner: ImageView

        init {
            //tvName = itemView.findViewById(R.id.tvName)
            imgBanner = itemView.findViewById(R.id.imgBanner)
        }
        fun bind(caseType: String) = itemView.run {
            when(caseType) {
                "red" -> imgBanner.setImageResource(R.drawable.ic_chest_red)
                "green" -> imgBanner.setImageResource(R.drawable.ic_chest_green)
                "yellow" -> imgBanner.setImageResource(R.drawable.ic_chest)
                "blue" -> imgBanner.setImageResource(R.drawable.ic_chest_blue)
            }

        }
    }

}
