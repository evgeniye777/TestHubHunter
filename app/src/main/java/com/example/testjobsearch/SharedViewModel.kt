package com.example.testjobsearch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class SharedViewModel : ViewModel() {
    private val _message = MutableLiveData<String>() // Поле для хранения строки
    val message: LiveData<String> get() = _message // LiveData для наблюдения

    // Метод для установки строки
    fun setMessage(msg: String) {
        _message.value = msg
    }
}