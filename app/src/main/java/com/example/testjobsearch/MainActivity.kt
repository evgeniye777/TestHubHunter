package com.example.testjobsearch

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.testjobsearch.databinding.ActivityMainBinding
import com.example.testjobsearch.ui.like.LikeFragment
import com.example.testjobsearch.ui.sours.SoursFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageViews: Array<ImageView>
    private lateinit var textViews: Array<TextView>
    private lateinit var navController: NavController

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var dataJson: DataJsonClasses

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        dataJson = DataJsonClasses()
        sharedViewModel.setDataJson(dataJson)

        if (savedInstanceState == null) {
            replaceFragment(SoursFragment.newInstance(dataJson), "SOURS_FRAGMENT")
        }
        supportActionBar?.hide()

        val imgSours:    ImageView = findViewById(R.id.img_sours)
        val imgLike:     ImageView = findViewById(R.id.img_like)
        val imgResponse: ImageView = findViewById(R.id.img_responses)
        val imgMessage:  ImageView = findViewById(R.id.img_message)
        val imgPorfile:  ImageView = findViewById(R.id.img_profile)
        imageViews = arrayOf(imgSours, imgLike,imgResponse,imgMessage,imgPorfile)

        val textSours:    TextView = findViewById(R.id.text_sours)
        val textLike:     TextView = findViewById(R.id.text_like)
        val textResponse: TextView = findViewById(R.id.text_responses)
        val textMessage:  TextView = findViewById(R.id.text_message)
        val textPorfile:  TextView = findViewById(R.id.text_profile)
        textViews = arrayOf(textSours, textLike,textResponse,textMessage,textPorfile)
        changeColorTextImage(0)

        val myLinear_menu_sours: LinearLayout = findViewById(R.id.menu_sours)
        myLinear_menu_sours.setOnClickListener {
            changeColorTextImage(0)
            replaceFragment(SoursFragment.newInstance(dataJson), "SOURS_FRAGMENT")
            //navController.navigate(R.id.navigation_sours, null, null, null)
        }
        val myLinear_menu_like: LinearLayout = findViewById(R.id.menu_like)
        myLinear_menu_like.setOnClickListener {
            changeColorTextImage(1)
            //navController.navigate(R.id.navigation_like, null, null, null)
            replaceFragment(LikeFragment.newInstance(dataJson), "LIKE_FRAGMENT")
        }
        val myLinear_menu_responses: LinearLayout = findViewById(R.id.menu_responses)
        myLinear_menu_responses.setOnClickListener {
            changeColorTextImage(2)
            //navController.navigate(R.id.navigation_responses, null, null, null)
        }
        val myLinear_menu_message: LinearLayout = findViewById(R.id.menu_message)
        myLinear_menu_message.setOnClickListener {
            changeColorTextImage(3)
            //navController.navigate(R.id.navigation_message, null, null, null)
        }
        val myLinear_menu_profile: LinearLayout = findViewById(R.id.menu_profile)
        myLinear_menu_profile.setOnClickListener {
            changeColorTextImage(4)
            //navController.navigate(R.id.navigation_profile, null, null, null)
        }
    }


    private fun changeColorTextImage(n: Int) {
        for (i: Int in 0..imageViews.size-1) {
            if (i==n) {
                imageViews.get(i).imageTintList = ColorStateList.valueOf(getColor(R.color.menu_click))
                textViews.get(i).setTextColor(ContextCompat.getColor(this,R.color.menu_click));
            }
            else {
                imageViews.get(i).imageTintList = ColorStateList.valueOf(getColor(R.color.menu_not_click))
                textViews.get(i).setTextColor(ContextCompat.getColor(this,R.color.menu_not_click));
            }
        }
    }

    fun vivodMesage(text: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Message")
            .setMessage(text)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        // Проверяем, существует ли фрагмент
        val existingFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.beginTransaction().apply {
            if (existingFragment == null) {
                // Фрагмент еще не создан, создаем новый
                add(R.id.fragment_container, fragment, tag)
            } else {
                // Фрагмент уже существует, просто показываем его
                show(existingFragment)
            }

            // Скрываем все остальные фрагменты
            supportFragmentManager.fragments.forEach { f ->
                if (f != existingFragment) {
                    hide(f)
                }
            }

            commit()
        }
    }
}
//https\://services.gradle.org/distributions/gradle-8.12.1-bin.zip