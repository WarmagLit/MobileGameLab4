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
import com.tsu.mobilegamelab4.cases.inventory.Case

class MyAdapter(private val context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var cases_list = ArrayList<Case>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.tvName.text = String.format("Row number  %d ", position)

        holder.imgBanner.setImageResource(R.drawable.ic_chest)

    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var tvName: TextView
        var imgBanner: ImageView

        init {
            //tvName = itemView.findViewById(R.id.tvName)
            imgBanner = itemView.findViewById(R.id.imgBanner)
        }
    }

    fun addCase() {
        
    }
}
