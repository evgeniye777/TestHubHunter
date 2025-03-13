package com.example.testjobsearch.ui.sours


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testjobsearch.DataJsonClasses
import com.example.testjobsearch.R
import com.example.testjobsearch.SharedViewModel
import com.example.testjobsearch.databinding.FragmentSoursBinding

class SoursFragment : Fragment() {

    private var _binding: FragmentSoursBinding? = null

    private lateinit var recyclerViewRecommends: RecyclerView
    private lateinit var adapterRecommends: AdapterRecommends

    private lateinit var recyclerViewVacancies: RecyclerView
    private lateinit var adapterVacancies: AdapterVacancies

    private lateinit var but_all_vacancies: Button
    private lateinit var img_but_back: ImageView

    private lateinit var layout_vacancies_and_correspondence: LinearLayout
    private lateinit var l_vacancies: TextView

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var dataJson: DataJsonClasses
    private val binding get() = _binding!!

    // Метод для создания нового экземпляра фрагмента
    companion object {
        fun newInstance(dataJson0: DataJsonClasses): SoursFragment {
            val fragment = SoursFragment()
            fragment.dataJson = dataJson0
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        // Наблюдаем за изменениями в dataJson
        sharedViewModel.dataJson.observe(viewLifecycleOwner, Observer { data ->
            updateUI(data)
        })

        _binding = FragmentSoursBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var responseData = dataJson.parseJson(context)

        recyclerViewRecommends = binding.recyclerViewRecommends
        recyclerViewRecommends.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Создаем адаптер для рекомендаций и устанавливаем его
        adapterRecommends = AdapterRecommends(responseData.offers)
        recyclerViewRecommends.adapter = adapterRecommends

        recyclerViewVacancies = binding.recyclerViewVacancies
        recyclerViewVacancies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // Создаем адаптер для вакансий и устанавливаем его
        adapterVacancies = AdapterVacancies(responseData.vacancies.toMutableList(),false,3)
        recyclerViewVacancies.adapter = adapterVacancies

        img_but_back = binding.imgButBack

        layout_vacancies_and_correspondence = binding.layoutVacanciesAndCorrespondence

        l_vacancies = binding.lVacancies

        //Кнопка для вывода всех вакансии
        but_all_vacancies = binding.butAllVacancies
        but_all_vacancies.text = getVacanciesMessage(responseData.vacancies.size)
        but_all_vacancies.setOnClickListener {
            but_all_vacancies.visibility = View.GONE
            recyclerViewRecommends.visibility = View.GONE
            layout_vacancies_and_correspondence.visibility = View.VISIBLE
            l_vacancies.text = getVacanciesMessage(responseData.vacancies.size,true)
            img_but_back.setImageResource(R.drawable.img_arrow)

            adapterVacancies = AdapterVacancies(responseData.vacancies)
            recyclerViewVacancies.adapter = adapterVacancies
        }
        //иконка_кнопка для возврата в предыдущее состояние
        img_but_back.setOnClickListener {
            layout_vacancies_and_correspondence.visibility = View.GONE
            but_all_vacancies.visibility = View.VISIBLE
            recyclerViewRecommends.visibility = View.VISIBLE
            img_but_back.setImageResource(R.drawable.img_sours)

            adapterVacancies = AdapterVacancies(responseData.vacancies,false,3)
            recyclerViewVacancies.adapter = adapterVacancies
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
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
    private fun updateUI(data: DataJsonClasses) {
        dataJson = data
    }

}
