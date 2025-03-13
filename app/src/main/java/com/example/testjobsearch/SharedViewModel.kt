package com.example.testjobsearch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class SharedViewModel : ViewModel() {
    private val _dataJson = MutableLiveData<DataJsonClasses>()
    val dataJson: LiveData<DataJsonClasses> get() = _dataJson

    fun setDataJson(data: DataJsonClasses) {
        _dataJson.value = data
    }
}