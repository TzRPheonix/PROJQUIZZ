package com.example.projquizz.database

import androidx.room.*

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions")
    fun getAll(): List<Question>

    @Insert
    fun insert(question: Question)

    @Delete
    fun delete(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(questions: List<Question>)

    // Permet de récuperer les questions d'un Quizz grâce à son id
    @Query("SELECT * FROM questions WHERE quizzId = :quizzId")
    fun getQuestionsByQuizzId(quizzId: Int): List<Question>
}