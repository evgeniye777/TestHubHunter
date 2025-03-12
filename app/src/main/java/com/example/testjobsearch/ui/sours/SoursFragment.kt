package com.example.testjobsearch.ui.sours

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testjobsearch.DataJsonClasses
import com.example.testjobsearch.Offer
import com.example.testjobsearch.R
import com.example.testjobsearch.ResponseData
import com.example.testjobsearch.databinding.FragmentSoursBinding
import com.google.gson.Gson

class SoursFragment : Fragment() {

    private var _binding: FragmentSoursBinding? = null

    private lateinit var recyclerViewRecommends: RecyclerView
    private lateinit var adapterRecommends: AdapterRecommends

    private lateinit var recyclerViewVacancies: RecyclerView
    private lateinit var adapterVacancies: AdapterVacancies

    private var dataJson: DataJsonClasses = DataJsonClasses()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoursBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerViewRecommends = binding.recyclerViewRecommends
        recyclerViewRecommends.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        var responseData = dataJson.parseJson(context)
        vivodMesage(context,responseData.toString())

        // Создаем адаптер для рекомендаций и устанавливаем его
        adapterRecommends = AdapterRecommends(responseData.offers)
        recyclerViewRecommends.adapter = adapterRecommends

        recyclerViewVacancies = binding.recyclerViewVacancies
        recyclerViewVacancies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // Создаем список данных для рекоммендаций
        val itemsVacancies = listOf(
            ItemVacancy(R.drawable.img_heart, "Сейчас просматривают 1 человек","UI/UX Designer","Минск","Мобирикс","Опыт от 1 года до 3 лет","Опубликовано 20 февраля"),
            ItemVacancy(R.drawable.img_heart, "Сейчас просматривают 1 человек","UI/UX Designer","Минск","Мобирикс","Опыт от 1 года до 3 лет","Опубликовано 20 февраля"),
            ItemVacancy(R.drawable.img_heart, "Сейчас просматривают 1 человек","UI/UX Designer","Минск","Мобирикс","Опыт от 1 года до 3 лет","Опубликовано 20 февраля")
        )
        // Создаем адаптер для рекомендаций и устанавливаем его
        adapterVacancies = AdapterVacancies(itemsVacancies)
        recyclerViewVacancies.adapter = adapterVacancies


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun vivodMesage(context: Context?,text: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Message")
            .setMessage(text)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
