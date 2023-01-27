package com.example.projquizz.ajoutBDD

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.projquizz.database.*
import org.json.JSONObject

class LoadActivity(application: Application) : AndroidViewModel(application) {
    private val questionDao: QuestionDao = QuizzDatabase.getDatabase(application).questionDao
    private val quizzDao: QuizzDao = QuizzDatabase.getDatabase(application).quizzDao
    private val contentResolver = application.contentResolver

    // Importe les questions dans la base de données
    fun importQuestions(uri: Uri) {
        val task = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Void, Unit>() {
            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg params: Void?) {
                try {
                    val inputStream = contentResolver.openInputStream(uri)
                    inputStream?.use {
                        val jsonString = it.bufferedReader().use { reader -> reader.readText() }
                        val json = JSONObject(jsonString)
                        val quizz = Quizz(0,json.getString("quizzName"), json.getString("category"))
                        quizzDao.insert(quizz)
                        val quizzId = quizzDao.getLastQuizzId().id
                        val questionsJson = json.getJSONArray("questions")
                        for (i in 0 until questionsJson.length()) {
                            val questionJson = questionsJson.getJSONObject(i)
                            val question = Question(0, quizzId, questionJson.getString("questionStr"), questionJson.getString("repA"), questionJson.getString("repB"), questionJson.getString("repC"), questionJson.getString("repD"), questionJson.getString("reponse"))
                            questionDao.insert(question)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("MainViewModel", "Error importing JSON: ${e.message}")
                }
            }
        }
        task.execute()
    }
}