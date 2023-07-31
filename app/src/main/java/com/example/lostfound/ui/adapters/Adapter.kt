package com.example.lostfound.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lostfound.R
import com.example.lostfound.data.models.Item

class Adapter(private val items: ArrayList<Item>, private val onClickObject: Adapter.MyOnClick)
    : RecyclerView.Adapter<Adapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var itemImageView = itemView.findViewById<ImageView>(R.id.carousel_image_view)

        fun setData(itemImageId: Int){
            itemImageView.setImageResource(itemImageId)
        }
    }
    interface MyOnClick{
        fun onClick(v: View?, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val itemImageId = item.imageId
        holder.setData(itemImageId)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}