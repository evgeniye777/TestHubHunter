package com.example.testjobsearch.ui.sours

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testjobsearch.Offer
import com.example.testjobsearch.R

class AdapterRecommends(private val items: List<Offer>) : RecyclerView.Adapter<AdapterRecommends.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout_click_rekomend: LinearLayout = view.findViewById(R.id.layout_click_recomend)
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val textView_text: TextView = view.findViewById(R.id.item_text)
        val textView_Up: TextView = view.findViewById(R.id.item_up)
        val recomendObject: FrameLayout = view.findViewById(R.id.recomend_object_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_for_one_recommendation, parent, false)
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
        val id: Int = choiceImage(item.id)
        if (id!=-1) {
            holder.imageView.visibility = View.VISIBLE
            holder.imageView.setImageResource(id) }
        else {
            holder.imageView.visibility = View.INVISIBLE }
        holder.textView_text.text = item.title
        trimTextToThreeLines(holder.textView_text)
        if (item.button?.text == null) {
            holder.textView_Up.visibility = View.INVISIBLE
        }
        holder.layout_click_rekomend.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                holder.itemView.context.startActivity(intent)
            }catch (e: Exception) {}
        }
    }

    override fun getItemCount() = items.size
    private fun choiceImage(id: String):Int {
        if (id==null) {return -1}
        if (id == "near_vacancies") {return R.drawable.img_vacancies_for_recommends}
        else if (id == "level_up_resume") {return R.drawable.img_star_for_recomends}
        else if (id == "temporary_job") {return R.drawable.img_list_ok_for_recomends}
        else return -1
    }

    fun trimTextToThreeLines(textView: TextView) {
        val originalText = textView.text.toString()
        textView.text = originalText // Устанавливаем текст, чтобы получить его размеры

        // Измеряем количество строк
        val layout = textView.layout
        if (layout != null && layout.lineCount > 3) {
            // Получаем индекс последнего символа, который помещается в три строки
            val lastLineEndIndex = layout.getLineEnd(2) // Индекс конца третьей строки
            val trimmedText = originalText.substring(0, lastLineEndIndex)

            // Находим последний пробел в обрезанном тексте
            val lastSpaceIndex = trimmedText.lastIndexOf(" ")
            if (lastSpaceIndex != -1) {
                // Обрезаем текст до последнего пробела
                textView.text = trimmedText.substring(0, lastSpaceIndex) + "..."
            } else {
                // Если пробелов нет, просто обрезаем текст
                textView.text = trimmedText + "..."
            }
        }
    }
}