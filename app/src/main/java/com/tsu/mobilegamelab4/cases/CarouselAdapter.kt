package com.tsu.mobilegamelab4.cases

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tsu.mobilegamelab4.R

class MyAdapter(private val context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text = String.format("Row number  %d ", position)
        if (position % 2 == 0) {
            holder.imgBanner.setBackgroundColor(Color.RED)
        } else {
            holder.imgBanner.setBackgroundColor(Color.GREEN)
        }
    }

    override fun getItemCount(): Int {
        return 15
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var imgBanner: ImageView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            imgBanner = itemView.findViewById(R.id.imgBanner)
        }
    }
}
