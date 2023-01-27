package com.example.projquizz.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuizzDao {
    @Query("SELECT * FROM quizz")
    fun getAllQuizz(): List<Quizz>

    @Insert
    fun insert(quizz: Quizz)

    @Delete
    fun delete(quizz: Quizz)

    @Query("SELECT * FROM questions WHERE quizzId = :quizzId")
    fun getQuestionsByQuizzId(quizzId: Int): List<Question>

    @Query("SELECT * FROM Quizz ORDER BY id DESC LIMIT 1")
    fun getLastQuizzId(): Quizz

    @Query("SELECT DISTINCT category FROM quizz")
    fun getAllCategories(): LiveData<List<String>>

    @Query("SELECT * FROM quizz WHERE category = :category")
    fun getQuizzesByCategory(category: String): List<Quizz>
}