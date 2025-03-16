package com.example.testjobsearch.ui.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testjobsearch.ResponseData
import com.example.testjobsearch.SharedViewModel
import com.example.testjobsearch.databinding.FragmentLikeBinding
import com.example.testjobsearch.ui.sours.AdapterVacancies

class LikeFragment : Fragment(), AdapterVacancies.OnVacancyUpdateListener {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentLikeBinding? = null

    private lateinit var recyclerViewVacancies: RecyclerView
    private lateinit var adapterVacancies: AdapterVacancies

    private lateinit var l_vacancies_like: TextView

    private lateinit var responseData: ResponseData
    private val binding get() = _binding!!

    companion object {
        fun newInstance(responseJson0: ResponseData): LikeFragment {
            val fragment = LikeFragment()
            fragment.responseData = responseJson0
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerViewVacancies = binding.recyclerViewLikeVacancies
        recyclerViewVacancies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // Создаем адаптер для вакансий и устанавливаем его
        adapterVacancies = AdapterVacancies(responseData.vacancies,sharedViewModel,requireContext(),true)

        l_vacancies_like = binding.lVacanciesLike
        l_vacancies_like.text = getVacanciesMessage(adapterVacancies.itemCount,true)

        adapterVacancies.setListener(this)
        recyclerViewVacancies.adapter = adapterVacancies

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setMessage("del")
    }
    fun vivodMesage(text: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Message")
            .setMessage(text)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
    fun getVacanciesMessage(vacanciesCount: Int, notYet: Boolean = false): String {
        val word = when {
            vacanciesCount % 10 == 1 && vacanciesCount % 100 != 11 -> "вакансия"
            vacanciesCount % 10 in 2..4 && (vacanciesCount % 100 !in 12..14) -> "вакансии"
            else -> "вакансий"
        }
        if (notYet) {return "$vacanciesCount $word"}
        else {return "Еще $vacanciesCount $word"}
    }
    fun updateList() {
        adapterVacancies = AdapterVacancies(responseData.vacancies,sharedViewModel,requireContext(),true)
        recyclerViewVacancies.adapter = adapterVacancies
        l_vacancies_like.text = getVacanciesMessage(adapterVacancies.itemCount,true)
        adapterVacancies.setListener(this) // Устанавливаем слушатель здесь
        sharedViewModel.setMessage("del")
    }

    override fun onVacancyUpdate() {
        updateList() // Вызываем метод обновления списка
    }
}