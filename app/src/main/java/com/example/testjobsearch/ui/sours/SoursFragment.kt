package com.example.testjobsearch.ui.sours


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testjobsearch.R
import com.example.testjobsearch.ResponseData
import com.example.testjobsearch.SharedViewModel
import com.example.testjobsearch.databinding.FragmentSoursBinding
import com.google.gson.Gson


class SoursFragment : Fragment() {

    private lateinit var listener: MyFragmentListener

    interface MyFragmentListener {
        fun getMyResponseDate(): ResponseData
    }
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentSoursBinding? = null

    private lateinit var recyclerViewRecommends: RecyclerView
    private lateinit var adapterRecommends: AdapterRecommends

    private lateinit var recyclerViewVacancies: RecyclerView
    private lateinit var adapterVacancies: AdapterVacancies

    private lateinit var but_all_vacancies: Button
    private lateinit var img_but_back: ImageView

    private lateinit var layout_vacancies_and_correspondence: LinearLayout
    private lateinit var l_vacancies: TextView

    private lateinit var responseData: ResponseData
    private val binding get() = _binding!!
    private var state: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MyFragmentListener) {
            //активация интерфейса с активностью
            listener = context
        } else {
            throw ClassCastException("$context must implement MyFragmentListener")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            try {
                //получаем экземпляр responeDate из MainActivity
                responseData = listener.getMyResponseDate()
            }catch (e:Exception) {
                //закрываем данную ссесию фрагмента, т к без responseDate нет смысла продолжать работать (если это произойдет то потребуется снова нажать на пункт меню)
                parentFragmentManager.beginTransaction().remove(this).commit()
            }
        _binding = FragmentSoursBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerViewRecommends = binding.recyclerViewRecommends
        recyclerViewRecommends.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Создаем адаптер для рекомендаций и устанавливаем его
        adapterRecommends = AdapterRecommends(responseData.offers)
        recyclerViewRecommends.adapter = adapterRecommends

        recyclerViewVacancies = binding.recyclerViewVacancies
        recyclerViewVacancies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // Создаем адаптер для вакансий и устанавливаем его
        adapterVacancies = AdapterVacancies(responseData.vacancies.toMutableList(),sharedViewModel,requireContext(),false,3)
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

            adapterVacancies = AdapterVacancies(responseData.vacancies,sharedViewModel,requireContext())
            recyclerViewVacancies.adapter = adapterVacancies
            state = true;
        }
        // обрабочик иконка_кнопка для возврата в предыдущее состояние
        img_but_back.setOnClickListener {
            layout_vacancies_and_correspondence.visibility = View.GONE
            but_all_vacancies.visibility = View.VISIBLE
            recyclerViewRecommends.visibility = View.VISIBLE
            img_but_back.setImageResource(R.drawable.img_sours)

            adapterVacancies = AdapterVacancies(responseData.vacancies,sharedViewModel,requireContext(),false,3)
            recyclerViewVacancies.adapter = adapterVacancies
            state = false
        }
        return root
    }

    fun getVacanciesMessage(vacanciesCount: Int, notYet: Boolean = false): String {
        //функция для склонения "Еще n вакансий" или "n вакансий"
        val word = when {
            vacanciesCount % 10 == 1 && vacanciesCount % 100 != 11 -> "вакансия"
            vacanciesCount % 10 in 2..4 && (vacanciesCount % 100 !in 12..14) -> "вакансии"
            else -> "вакансий"
        }
        if (notYet) {return "$vacanciesCount $word"}
        else {return "Еще $vacanciesCount $word"}
    }

    fun updateList() {
        //функция для обновления списка при измении (нажатии лайков)
        if (state) {
            adapterVacancies = AdapterVacancies(
                responseData.vacancies,
                sharedViewModel,
                requireContext(),
                false
            )
            recyclerViewVacancies.adapter = adapterVacancies
            l_vacancies.text = getVacanciesMessage(adapterVacancies.itemCount,true)
        }
        else {
            adapterVacancies = AdapterVacancies(responseData.vacancies,sharedViewModel,requireContext(),false,3)
            recyclerViewVacancies.adapter = adapterVacancies
        }
    }
}
