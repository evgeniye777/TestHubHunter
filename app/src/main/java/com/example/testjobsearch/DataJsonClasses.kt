package com.example.testjobsearch
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader

class DataJsonClasses {

    private val gson = Gson()

    fun readJsonFromAssets(context: Context?, fileName: String): String {
        val inputStream = context?.assets?.open(fileName)
        val reader = InputStreamReader(inputStream)
        return reader.use { it.readText() }
    }

    fun parseJson(context: Context?): ResponseData {
        val jsonStringFromAssets = readJsonFromAssets(context, "data.json")
        // Десериализация JSON в объект ResponseData
        return gson.fromJson(jsonStringFromAssets, ResponseData::class.java)
    }

    fun writeJsonToFile(context: Context?, responseData: ResponseData):String? {
        try {val jsonString = gson.toJson(responseData)
            val file = File(context?.filesDir, "data.json")
            FileOutputStream(file).use { outputStream ->
            outputStream.write(jsonString.toByteArray())
        }} catch (e: Exception) {
            Log.e("DataJsonClasses", "Error writing JSON to file: ${e.message}")
            return "Error writing JSON to file: ${e.message}"
        }
        return null;
    }

    fun getRequest() {
        // Ваш код для getRequest
    }
}
data class ResponseData(
    val offers: MutableList<Offer>,
    val vacancies: MutableList<Vacancy>
){
    fun countFavoriteVacancies(): Int {
        return vacancies.count { it.isFavorite }
    }
}

data class Offer(
    val id: String,
    val title: String,
    val link: String,
    val button: Button? = null
)

data class Button(
    val text: String
)

data class Vacancy(
    val id: String,
    val lookingNumber: Int,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    var isFavorite: Boolean,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Int,
    val description: String,
    val responsibilities: String,
    val questions: List<String>
)

data class Address(
    val town: String,
    val street: String,
    val house: String
)

data class Experience(
    val previewText: String,
    val text: String
)

data class Salary(
    val short: String? = null,
    val full: String
)