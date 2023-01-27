package com.example.projquizz.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Question::class, Quizz::class], version = 1, exportSchema = false)
abstract class  QuizzDatabase : RoomDatabase() {
    abstract val questionDao: QuestionDao
    abstract val quizzDao: QuizzDao
    companion object {
        @Volatile
        private var INSTANCE: QuizzDatabase? = null

        fun getDatabase(context: Context): QuizzDatabase {
            val tempInstance = INSTANCE
            Log.e("result1", tempInstance.toString())
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizzDatabase::class.java,
                    "quizz_database"
                ).build()
                INSTANCE = instance
                Log.e("result2", INSTANCE.toString())
                return instance
            }
        }
    }
}