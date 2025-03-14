package com.example.testjobsearch.ui.responses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResponsesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Отклики"
    }
    val text: LiveData<String> = _text
}