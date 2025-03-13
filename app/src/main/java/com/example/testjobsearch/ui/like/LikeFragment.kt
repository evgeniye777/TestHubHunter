package com.example.testjobsearch.ui.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testjobsearch.DataJsonClasses
import com.example.testjobsearch.SharedViewModel
import com.example.testjobsearch.databinding.FragmentLikeBinding
import com.example.testjobsearch.ui.sours.AdapterVacancies
import com.example.testjobsearch.ui.sours.SoursFragment

class LikeFragment : Fragment() {

    private var _binding: FragmentLikeBinding? = null

    private lateinit var recyclerViewVacancies: RecyclerView
    private lateinit var adapterVacancies: AdapterVacancies

    private lateinit var l_vacancies: TextView

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var dataJson: DataJsonClasses
    private val binding get() = _binding!!

    companion object {
        fun newInstance(dataJson0: DataJsonClasses): LikeFragment {
            val fragment = LikeFragment()
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
        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var responseData = dataJson.parseJson(context)

        recyclerViewVacancies = binding.recyclerViewLikeVacancies
        recyclerViewVacancies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // Создаем адаптер для вакансий и устанавливаем его
        adapterVacancies = AdapterVacancies(responseData.vacancies,true)
        recyclerViewVacancies.adapter = adapterVacancies

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

    private fun updateUI(data: DataJsonClasses) {
        dataJson = data
    }
}