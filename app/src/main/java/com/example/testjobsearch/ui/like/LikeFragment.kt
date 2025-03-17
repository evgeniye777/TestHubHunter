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
import com.google.gson.Gson

class LikeFragment : Fragment(), AdapterVacancies.OnVacancyUpdateListener {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentLikeBinding? = null

    private lateinit var recyclerViewVacancies: RecyclerView
    private lateinit var adapterVacancies: AdapterVacancies

    private lateinit var l_vacancies_like: TextView

    private lateinit var responseData: ResponseData
    private val binding get() = _binding!!
    //для востановления состояния
    private val gson = Gson()

    companion object {
        fun newInstance(responseJson0: ResponseData): LikeFragment {
            val fragment = LikeFragment()
            //передача преобразованных данных json при первом запуске фрагмента
            fragment.responseData = responseJson0
            return fragment
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Сериализуем responseData в JSON и сохраняем в Bundle
        val jsonString = gson.toJson(responseData)
        outState.putString("responseData", jsonString)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            // Восстанавливаем responseData из Bundle
            val jsonString = savedInstanceState.getString("responseData")
            responseData = gson.fromJson(jsonString, ResponseData::class.java)
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
        //через интерфейс передаем информацию главной активности, чтобы удалить уведомление о новых лайках (т к пользователь зашел в Избранное)
        sharedViewModel.setMessage("del")
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
        //функция для обновления адаптера, используется состороны главной активности и через интерфейс с адаптером в момент установки или адаления лайка
        adapterVacancies = AdapterVacancies(responseData.vacancies,sharedViewModel,requireContext(),true)
        recyclerViewVacancies.adapter = adapterVacancies
        l_vacancies_like.text = getVacanciesMessage(adapterVacancies.itemCount,true)
        adapterVacancies.setListener(this) // Устанавливаем слушатель здесь
        sharedViewModel.setMessage("del")
    }

    override fun onVacancyUpdate() {
        // Метод интерфейса для вызывания метода обновления списка
        updateList()
    }
}