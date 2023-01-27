package com.example.projquizz.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "questions", foreignKeys = [ForeignKey(entity = Quizz::class, parentColumns = ["id"], childColumns = ["quizzId"])])
data class Question (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "quizzId") var quizzId: Int,
    @ColumnInfo(name = "questionStr") val questionStr: String,
    @ColumnInfo(name = "repA") val repA: String,
    @ColumnInfo(name = "repB") val repB: String,
    @ColumnInfo(name = "repC") val repC: String,
    @ColumnInfo(name = "repD") val repD: String,
    @ColumnInfo(name = "reponse") val reponse: String
)
