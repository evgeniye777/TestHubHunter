package com.example.testjobsearch
import android.content.Context
import com.google.gson.Gson
import java.io.InputStreamReader

class DataJsonClasses {
    fun readJsonFromAssets(context: Context?, fileName: String): String {
        val inputStream = context?.assets?.open(fileName)
        val reader = InputStreamReader(inputStream)
        return reader.use { it.readText() }
    }

    fun parseJson(context: Context?): ResponseData{
        val jsonStringFromAssets = readJsonFromAssets(context, "data.json")
        val gson = Gson()

        // Десериализация JSON в объект ResponseData
        val responseData: ResponseData = gson.fromJson(jsonStringFromAssets, ResponseData::class.java)

        // Проверка содержимого
        return responseData
    }
}
data class ResponseData(
    val offers: List<Offer>,
    val vacancies: List<Vacancy>
)

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
    val isFavorite: Boolean,
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