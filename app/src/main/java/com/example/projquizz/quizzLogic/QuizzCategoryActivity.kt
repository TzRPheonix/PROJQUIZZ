package com.example.projquizz.quizzLogic

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projquizz.MainActivity
import com.example.projquizz.R
import com.example.projquizz.database.Quizz
import kotlinx.android.synthetic.main.activity_quizz_category.*

class QuizzCategoryActivity : AppCompatActivity() {

    private lateinit var viewModel: QuizzCategoryViewModel

    // Ajout un bouton pour chaque catégorie
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz_category)

        viewModel = ViewModelProviders.of(this).get(QuizzCategoryViewModel::class.java)
        viewModel.categories.observe(this, Observer { categories ->
            for (category in categories) {
                Log.e("result3", category)
                val button = Button(this)
                button.text = category
                button.setOnClickListener {
                    viewModel.getRandomQuizzByCategory(category, this)
                }
                category_container.addView(button)
                button.setTextColor(Color.WHITE)
                button.setTypeface(null, Typeface.BOLD)
                button.setTextSize(20f)
                button.setBackgroundColor(Color.parseColor("#F44336"))
            }
        })
    }

    // Bouton retour
    fun onClickRetour(view: View){
        val intent = Intent(view.getContext(), MainActivity::class.java)
        view.getContext().startActivity(intent)
    }
}
