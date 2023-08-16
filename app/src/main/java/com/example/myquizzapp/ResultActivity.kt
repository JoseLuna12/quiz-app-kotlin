package com.example.myquizzapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    private var userName: String? = null
    private var score: Int? = null
    private var questions: Int? = null

    private var nameView: TextView? = null
    private var scoreView: TextView? = null
    private var finishButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        userName = intent.getStringExtra(Constants.USER_NAME)
        score = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        questions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)

        nameView = findViewById(R.id.name)
        scoreView = findViewById(R.id.score)
        finishButton = findViewById(R.id.finishButton)

        finishButton?.setOnClickListener {
            playAgain()
        }
        setValues()
    }

    private fun setValues(){
        nameView?.text = userName
        scoreView?.text = "our score is ${score} out of ${questions}"
    }

    private fun playAgain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}