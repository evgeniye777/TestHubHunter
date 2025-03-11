package com.example.testjobsearch

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testjobsearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageViews: Array<ImageView>
    private lateinit var textViews: Array<TextView>
    private lateinit var navController: NavController

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

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
            navController.navigate(R.id.navigation_sours)
        }
        val myLinear_menu_like: LinearLayout = findViewById(R.id.menu_like)
        myLinear_menu_like.setOnClickListener {
            changeColorTextImage(1)
            navController.navigate(R.id.navigation_like)
        }
        val myLinear_menu_responses: LinearLayout = findViewById(R.id.menu_responses)
        myLinear_menu_responses.setOnClickListener {
            changeColorTextImage(2)
            navController.navigate(R.id.navigation_responses)
        }
        val myLinear_menu_message: LinearLayout = findViewById(R.id.menu_message)
        myLinear_menu_message.setOnClickListener {
            changeColorTextImage(3)
            navController.navigate(R.id.navigation_message)
        }
        val myLinear_menu_profile: LinearLayout = findViewById(R.id.menu_profile)
        myLinear_menu_profile.setOnClickListener {
            changeColorTextImage(4)
            navController.navigate(R.id.navigation_profile)
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
}
//https\://services.gradle.org/distributions/gradle-8.12.1-bin.zip