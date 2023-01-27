package com.example.projquizz.repository

import com.example.projquizz.database.Question
import com.example.projquizz.database.Quizz
import com.example.projquizz.database.QuizzDao

class QuizzRepository(private val quizzDao: QuizzDao) {
    //Plus utile
    fun getAllQuizz() : List<Quizz> = quizzDao.getAllQuizz()
    fun getQuestionsByQuizzId(quizzId: Int) : List<Question> = quizzDao.getQuestionsByQuizzId(quizzId)
}