package com.example.projquizz.quizzLogic

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projquizz.R
import com.example.projquizz.database.Question
import com.example.projquizz.database.Quizz
import com.example.projquizz.database.QuizzDatabase
import kotlinx.android.synthetic.main.activity_quizz_display.*

class QuizzActivity : AppCompatActivity() {

    private var score = 0
    private lateinit var quizz: Quizz
    private var currentQuestion = 0
    private lateinit var timer: CountDownTimer
    private lateinit var questions: List<Question>

    // Récupère la donnée dans un intent.extra
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz_display)
        val textViewScore: TextView = findViewById(R.id.textViewScore)
        val layout: LinearLayout = findViewById(R.id.layout)

        val extras = intent.extras
        if (extras != null) {
            quizz = extras.get("quizz") as Quizz
        }
        val quizzId = quizz.id
        RetrieveQuestionsTask(this).execute(quizzId.toLong())

        buttonSubmit.setOnClickListener {
            checkAnswer()
        }
    }

    // Change la question en cas de réponse ainsi que le timer
    private fun updateQuestion(questions: List<Question>) {
        this.questions = questions
        if (currentQuestion < questions.size) {
            val question = questions[currentQuestion]
            Log.e("afficheQuestion", question.toString())
            textViewQuestion.text = question.questionStr
            textViewQuestion.setTextColor(Color.parseColor("#F44336"))
            textViewQuestion.setTypeface(null, Typeface.BOLD)
            textViewQuestion.setTextSize(25f)
            radioButtonA.text = question.repA
            radioButtonA.textSize = 20f
            radioButtonB.text = question.repB
            radioButtonB.textSize = 20f
            radioButtonC.text = question.repC
            radioButtonC.textSize = 20f
            radioButtonD.text = question.repD
            radioButtonD.textSize = 20f
            timer = object : CountDownTimer(30000, 1000) {
                override fun onTick(msBeforeFinish: Long) {
                    textViewTimer.text = "Time remaining: " + msBeforeFinish / 1000
                    textViewTimer.setTextSize(20f)
                }

                override fun onFinish() {
                    textViewTimer.text = "Time's up!"
                    currentQuestion++
                    Toast.makeText(this@QuizzActivity, "Time's up! Question Skipped the answer was ${question.reponse}", Toast.LENGTH_SHORT).show()
                    updateQuestion(questions)
                }
            }.start()
        } else {
            textViewQuestion.text = "Quizz finished! Your score is $score"
            val buttonShare = Button(this)
            radioGroupAnswers.clearCheck()
            radioGroupAnswers.isEnabled = false
            buttonSubmit.isEnabled = false
            timer.cancel()
            buttonShare.text = "Share your score"
            buttonShare.setOnClickListener {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "I scored $score on ${quizz.name}!")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "Share score"))
            }
            layout.addView(buttonShare)
            buttonShare.setTextColor(Color.WHITE)
            buttonShare.setTypeface(null, Typeface.BOLD)
            buttonShare.setTextSize(20f)
            buttonShare.setBackgroundColor(Color.parseColor("#F44336"))
            radioGroupAnswers.isEnabled = false
        }
    }

    // Vérifie la réponse update le score et éteint le timer courant
    private fun checkAnswer() {
        if (radioGroupAnswers.checkedRadioButtonId != -1) {
            val selectedAnswer = findViewById<RadioButton>(radioGroupAnswers.checkedRadioButtonId).text.toString()
            val correctAnswer = questions[currentQuestion].reponse
            if (selectedAnswer == correctAnswer) {
                score++
                textViewScore.text = "Score: $score"
                textViewScore.setTextSize(20f)
                Log.e("displayScore",textViewScore.text.toString())
            }else{
                Toast.makeText(this@QuizzActivity, "The answer was $correctAnswer", Toast.LENGTH_SHORT).show()
            }
            timer.cancel()
            currentQuestion++
            updateQuestion(questions)
        }
    }

    // Function récupérant chaque question par l'id Quizz
    class RetrieveQuestionsTask(val context: QuizzActivity) : AsyncTask<Long, Void, List<Question>>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Long?): List<Question> {
            val questionDao = QuizzDatabase.getDatabase(context).questionDao
            val questions = questionDao.getQuestionsByQuizzId(params[0]!!.toInt())

            for (question in questions) {
                Log.e("questionBefore", question.questionStr)
            }
            return questions
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: List<Question>?) {
            super.onPostExecute(result)
            if (result != null) {
                for (question in result) {
                    Log.e("questionAfter", question.questionStr)
                }
                context.updateQuestion(result)
            }
        }
    }

}
