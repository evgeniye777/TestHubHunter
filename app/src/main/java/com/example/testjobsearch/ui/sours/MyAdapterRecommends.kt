package com.example.testjobsearch.ui.sours

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testjobsearch.R

class MyAdapterRecommends(private val items: List<MyItem>) : RecyclerView.Adapter<MyAdapterRecommends.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val textView: TextView = view.findViewById(R.id.item_text)
        val recomendObject: FrameLayout = view.findViewById(R.id.recomend_object_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_for_one_recommendations, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        if (position==0) {
            val p = holder.recomendObject.paddingLeft
            val layoutParams = holder.recomendObject.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(p*2, 0, 0, 0)
            holder.recomendObject.layoutParams = layoutParams
        }
        holder.imageView.setImageResource(item.imageResId) // Установите изображение
        holder.textView.text = item.text // Установите текст
    }

    override fun getItemCount() = items.size
}