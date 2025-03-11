package com.example.testjobsearch.ui.sours

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testjobsearch.R
import com.example.testjobsearch.databinding.FragmentSoursBinding

class SoursFragment : Fragment() {

    private var _binding: FragmentSoursBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapterRecommends
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoursBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = binding.recyclerView

        // Установите LayoutManager с горизонтальной ориентацией
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Создайте список данных
        val items = listOf(
            MyItem(R.drawable.img_star, "Поднять резюме в поиске"),
            MyItem(R.drawable.img_curtain, "Поднять резюме в поиске"),
            MyItem(R.drawable.img_list_ok, "Поднять резюме в поиске")
            // Добавьте больше элементов по мере необходимости
        )

        // Создайте адаптер и установите его
        adapter = MyAdapterRecommends(items)
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
