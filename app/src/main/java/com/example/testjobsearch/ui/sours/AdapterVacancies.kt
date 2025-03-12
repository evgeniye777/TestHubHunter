package com.example.testjobsearch.ui.sours

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testjobsearch.R

class AdapterVacancies(private val items: List<ItemVacancy>) : RecyclerView.Adapter<AdapterVacancies.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageLike: ImageView = view.findViewById(R.id.image_like)
        val textView_Watching: TextView = view.findViewById(R.id.text_watching)
        val textView_Post: TextView = view.findViewById(R.id.text_post)
        val textView_Town: TextView = view.findViewById(R.id.text_town)
        val textView_Company: TextView = view.findViewById(R.id.text_name_company)
        val textView_Experience: TextView = view.findViewById(R.id.text_experience)
        val textView_Published: TextView = view.findViewById(R.id.text_published)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_for_one_vacancy, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.imageLike.setImageResource(item.imageLikeId)
        holder.textView_Watching.text = item.textWatcher
        holder.textView_Post.text = item.textPost
        holder.textView_Town.text = item.textTown
        holder.textView_Company.text = item.textCompany
        holder.textView_Experience.text = item.textExperience
        holder.textView_Published.text = item.textPublished
    }

    override fun getItemCount() = items.size
}