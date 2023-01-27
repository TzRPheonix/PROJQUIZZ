package com.example.projquizz.repository

import com.example.projquizz.database.Question
import com.example.projquizz.database.QuestionDao

class QuestionRepository(private val questionDao: QuestionDao) {
    //Plus utile
    suspend fun getQuestions(): List<Question> {
        return questionDao.getAll()
    }
    fun retrieveQuestionsByQuizzId(quizzId: Int) = questionDao.getQuestionsByQuizzId(quizzId)
}