package com.example.testjobsearch.ui.like

import android.content.Context
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
import com.example.testjobsearch.ui.sours.SoursFragment.MyFragmentListener
import com.google.gson.Gson

class LikeFragment : Fragment(), AdapterVacancies.OnVacancyUpdateListener {
    private lateinit var listener: MyFragmentListener

    interface MyFragmentListener {
        fun getMyResponseDate(): ResponseData
    }
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentLikeBinding? = null

    private lateinit var recyclerViewVacancies: RecyclerView
    private lateinit var adapterVacancies: AdapterVacancies

    private lateinit var l_vacancies_like: TextView

    private lateinit var responseData: ResponseData
    private val binding get() = _binding!!
    //для востановления состояния
    private val gson = Gson()

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