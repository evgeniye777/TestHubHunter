package com.example.testjobsearch.ui.sours

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.testjobsearch.DataJsonClasses
import com.example.testjobsearch.MainActivity
import com.example.testjobsearch.R
import com.example.testjobsearch.ResponseData
import com.example.testjobsearch.SharedViewModel
import com.example.testjobsearch.Vacancy
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class AdapterVacancies(private val items: MutableList<Vacancy>,
                       private val sharedViewModel: SharedViewModel, private val filterFavorites: Boolean = false, private val maxItems: Int = items.size) : RecyclerView.Adapter<AdapterVacancies.MyViewHolder>() {

    private var listener: OnVacancyUpdateListener? = null
    fun setListener(listener: OnVacancyUpdateListener) {
        this.listener = listener
    }

    private val filteredItems: List<Vacancy> = if (filterFavorites) {
        items.filter { it.isFavorite } // Фильтруем только те, которые являются любимыми
    } else {
        items
    }
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
        val item = filteredItems[position]

        if (item.lookingNumber>0) {holder.textView_Watching.text = getViewingMessage(item.lookingNumber)}
        else {holder.textView_Watching.visibility = View.INVISIBLE}
        holder.imageLike.setImageResource(choiceImage(item.isFavorite))

        holder.textView_Post.text = item.title
        holder.textView_Town.text = item.address.town
        holder.textView_Company.text = item.company
        holder.textView_Experience.text = item.experience.previewText
        holder.textView_Published.text = getPublishedMessage(item.publishedDate)
        holder.imageLike.setOnClickListener {
            // Изменяем состояние isFavorite
            item.isFavorite = !item.isFavorite
           if (listener==null) { if (item.isFavorite) { sharedViewModel.setMessage("+1") }
            else { sharedViewModel.setMessage("-1") }}
            // Обновляем изображение
            holder.imageLike.setImageResource(choiceImage(item.isFavorite))
            // Уведомляем адаптер о том, что данные изменились
            notifyItemChanged(position)
            listener?.onVacancyUpdate()
        }

    }

    fun getViewingMessage(lookingNumber: Int): String {
        val word = when {
            lookingNumber % 10 == 1 && lookingNumber % 100 != 11 -> "человек"
            lookingNumber % 10 in 2..4 && (lookingNumber % 100 !in 12..14) -> "человека"
            else -> "человеков"
        }
        return "Сейчас просматривает $lookingNumber $word"
    }

    private fun choiceImage(isFavorite: Boolean):Int {
        if (isFavorite!= null&&isFavorite) {return R.drawable.img_heart_like}
        else return R.drawable.img_heart
    }

    fun getPublishedMessage(publishedDate: String): String {
        val date:LocalDate;
        try {
            date = LocalDate.parse(publishedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }catch (e: DateTimeParseException) {
            return ""
        }
        val day = date.dayOfMonth
        val month = date.monthValue

        val monthName = when (month) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> ""
        }
        return "Опубликовано $day $monthName"
    }
    override fun getItemCount() = minOf(filteredItems.size, maxItems)

    interface OnVacancyUpdateListener {
        fun onVacancyUpdate()
    }
}