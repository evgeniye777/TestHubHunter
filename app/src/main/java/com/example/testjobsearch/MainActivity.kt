package com.example.testjobsearch

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testjobsearch.databinding.ActivityMainBinding
import com.example.testjobsearch.ui.like.LikeFragment
import com.example.testjobsearch.ui.message.MessageFragment
import com.example.testjobsearch.ui.profile.ProfileFragment
import com.example.testjobsearch.ui.responses.ResponsesFragment
import com.example.testjobsearch.ui.sours.SoursFragment


class MainActivity : AppCompatActivity(),SoursFragment.MyFragmentListener,LikeFragment.MyFragmentListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageViews: Array<ImageView>
    private lateinit var textViews: Array<TextView>

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var dataJson: DataJsonClasses
    private lateinit var responseData: ResponseData

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataJson = DataJsonClasses()
        responseData = dataJson.parseJson(this)

        val img_new_like: ImageView = findViewById(R.id.img_red_circle)
        val text_quantity: TextView = findViewById(R.id.text_quantity)
        var n_like: Int = responseData.countFavoriteVacancies()
        if (n_like>0) {
            img_new_like.visibility = View.VISIBLE
            text_quantity.visibility = View.VISIBLE
            text_quantity.text = ""+n_like}
        //Отслеживаем добавление или удаление новых лайков
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        sharedViewModel.message.observe(this, { message ->
            if (message=="+1") {
                n_like++
                img_new_like.visibility = View.VISIBLE
                text_quantity.visibility = View.VISIBLE
                text_quantity.text = ""+n_like
            }
            else if (message=="-1") {
                n_like--
                if (n_like<=0) { n_like=0
                img_new_like.visibility = View.GONE
                text_quantity.visibility = View.GONE}
                else {
                    img_new_like.visibility = View.VISIBLE
                    text_quantity.visibility = View.VISIBLE
                    text_quantity.text = ""+n_like
                }
            }
            else if (message=="del") {
                n_like=0
                img_new_like.visibility = View.GONE
                text_quantity.visibility = View.GONE
            }
            dataJson.writeJsonToFile(this, responseData)
        })
        if (savedInstanceState == null) {
            replaceFragment(SoursFragment(), "SOURS_FRAGMENT")
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
            replaceFragment(SoursFragment(), "SOURS_FRAGMENT")
        }
        val myLinear_menu_like: LinearLayout = findViewById(R.id.menu_like)
        myLinear_menu_like.setOnClickListener {
            changeColorTextImage(1)
            replaceFragment(LikeFragment(), "LIKE_FRAGMENT")
        }
        val myLinear_menu_responses: LinearLayout = findViewById(R.id.menu_responses)
        myLinear_menu_responses.setOnClickListener {
            changeColorTextImage(2)
            //заглушка
            replaceFragment(ResponsesFragment(), "RESPONSES_FRAGMENT")
        }
        val myLinear_menu_message: LinearLayout = findViewById(R.id.menu_message)
        myLinear_menu_message.setOnClickListener {
            changeColorTextImage(3)
            //заглушка
            replaceFragment(MessageFragment(), "MESSAGE_FRAGMENT")
        }
        val myLinear_menu_profile: LinearLayout = findViewById(R.id.menu_profile)
        myLinear_menu_profile.setOnClickListener {
            changeColorTextImage(4)
            //заглушка
            replaceFragment(ProfileFragment(), "PROFILE_FRAGMENT")
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
                if (tag == "LIKE_FRAGMENT" &&existingFragment is LikeFragment) {
                    existingFragment.updateList()
                }
                else if (tag == "SOURS_FRAGMENT" &&existingFragment is SoursFragment) {
                    existingFragment.updateList()
                }
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

    override fun getMyResponseDate(): ResponseData {
        return responseData
    }
}