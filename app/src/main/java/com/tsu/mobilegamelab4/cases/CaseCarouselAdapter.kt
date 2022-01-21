package com.tsu.mobilegamelab4.cases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.cases.dragndrop.callback.DifferenceCallback

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
        var imgBanner: ImageView = itemView.findViewById(R.id.imgBanner)

        fun bind(caseType: String) = itemView.run {
            when(caseType) {
                CasesViewModel.RED -> imgBanner.setImageResource(R.drawable.ic_chest_red)
                CasesViewModel.GREEN -> imgBanner.setImageResource(R.drawable.ic_chest_green)
                CasesViewModel.YELLOW -> imgBanner.setImageResource(R.drawable.ic_chest)
                CasesViewModel.BLUE -> imgBanner.setImageResource(R.drawable.ic_chest_blue)
            }

        }
    }

}
