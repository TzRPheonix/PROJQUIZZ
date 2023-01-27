package com.example.projquizz.quizzLogic

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.projquizz.database.Quizz
import com.example.projquizz.database.QuizzDao
import com.example.projquizz.database.QuizzDatabase

class QuizzCategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val quizzDao: QuizzDao
    val categories: LiveData<List<String>>

    init {
        val database = QuizzDatabase.getDatabase(application)
        quizzDao = database.quizzDao
        categories = quizzDao.getAllCategories()
    }

    fun getRandomQuizzByCategory(category: String, context: Context) {
        GetRandomQuizzTask(quizzDao, context).execute(category)
    }

    class GetRandomQuizzTask(val quizzDao: QuizzDao, val context: Context) : AsyncTask<String, Void, Quizz?>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String): Quizz? {
            val category = params[0]
            val quizzesByCategory = quizzDao.getQuizzesByCategory(category)
            if (quizzesByCategory.isEmpty()) {
                return null
            }
            val randomIndex = (Math.random() * quizzesByCategory.size).toInt()
            return quizzesByCategory[randomIndex]
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Quizz?) {
            Log.e("result4", result.toString())
            if (result != null) {
                val intent = Intent(context, QuizzActivity::class.java)
                Log.e("result5", context.toString())
                intent.putExtra("quizz", result)
                context.startActivity(intent)
                Log.e("result6", context.toString())
            } else {
                // Au cas ou un Quizz serait supprimé pendant le chargement des boutons
                Toast.makeText(context, "No quizzes available for this category", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


