package com.example.myquizzapp

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.core.content.ContextCompat

class quizz_page_activity : AppCompatActivity(), OnClickListener {

    private var userName: String? = null

    private var questionsList: ArrayList<Question>? = null
    private var currentPosition = 0
    private var selectedOptionPos = 0
    private var goToNextQuestion = false

    private var score = 0

    private var activityQuestion: TextView? = null
    private var flagImage: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var progressText: TextView? = null

    private var optionOne: TextView? = null
    private var optionTwo: TextView? = null
    private var optionThree: TextView? = null
    private var optionFour: TextView? = null

    private var submitButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz_page)

        userName = intent.getStringExtra(Constants.USER_NAME)

        questionsList = Constants.getQuestions()
        activityQuestion = findViewById(R.id.activityQuestion)

        flagImage = findViewById(R.id.flagImage)
        progressBar = findViewById(R.id.pBar)
        progressText = findViewById(R.id.progressLabel)

        optionOne = findViewById(R.id.optionOne)
        optionTwo = findViewById(R.id.optionTwo)
        optionThree = findViewById(R.id.optionThree)
        optionFour = findViewById(R.id.optionFour)

        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)

        submitButton = findViewById(R.id.submitFlag)

        submitButton?.setOnClickListener(this)

        setQuestion()
    }

    private fun defaultOptionsView(){
        val optionsView = ArrayList<TextView>()
        optionOne?.let {
            optionsView.add(0, it)
        }
        optionTwo?.let {
            optionsView.add(1, it)
        }
        optionThree?.let {
            optionsView.add(2, it)
        }
        optionFour?.let {
            optionsView.add(3, it)
        }

        for (option in optionsView){
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedQuestion(textView: TextView, selection: Int){
        defaultOptionsView()
        selectedOptionPos = selection
        textView.typeface = Typeface.DEFAULT_BOLD
        textView.background = ContextCompat.getDrawable(this, R.drawable.active_option_border_bg)
    }

    private fun setQuestion() {
        val currentQuestion = questionsList!![currentPosition]
        activityQuestion?.text = currentQuestion.question

        flagImage?.setImageResource(currentQuestion.image)
        progressBar?.progress = currentPosition + 1
        progressText?.text = "${currentPosition} / ${progressBar?.max}"

        optionOne?.text = currentQuestion.optionOne
        optionTwo?.text = currentQuestion.optionTwo
        optionThree?.text = currentQuestion.optionThree
        optionFour?.text = currentQuestion.optionFour

        if(questionsList!!.size == currentPosition + 1){
            submitButton?.text = "Finish"
        }else {
            submitButton?.text = "Submit"
        }
    }

    private fun submitFunction(view: TextView, correct: TextView){
        val flag = questionsList!![currentPosition]
        if(selectedOptionPos+1 != flag.correctAnswer){
            view.background = ContextCompat.getDrawable(this, R.drawable.incorrect_option_border_bg)
        }else {
            score++
        }
        correct.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
        if(currentPosition == questionsList!!.size - 1){
            submitButton?.text = "Finish"
        }else {
            submitButton?.text = "Next question"
        }
        goToNextQuestion = true
    }

    private fun goToNextQuestionFunc(){
        if(currentPosition == questionsList!!.size - 1){
            Toast.makeText(this, "to Score es: $score", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(Constants.CORRECT_ANSWERS, score)
            intent.putExtra(Constants.TOTAL_QUESTIONS, progressBar?.max)
            startActivity(intent)
            finish()
            return
        }
            currentPosition++
            setQuestion()
            defaultOptionsView()
            goToNextQuestion = false
            selectedOptionPos = 0

    }

    private fun checkAnswer(){

        if(goToNextQuestion){
            goToNextQuestionFunc()
            return
        }

        val flag = questionsList!![currentPosition]

        var correct: TextView? = null
        when(flag.correctAnswer -1){
            0 -> {
                correct = optionOne
            }
            1 -> {
                correct = optionTwo
            }
            2 -> {
                correct = optionThree
            }
            3 -> {
                correct = optionFour
            }
        }

        when (selectedOptionPos) {
            0 -> optionOne?.let {
                correct?.let {cor ->
                    submitFunction(it, cor)
                }
            }
            1 -> optionTwo?.let {
                correct?.let {cor ->
                    submitFunction(it, cor)
                }
            }
            2 -> optionThree?.let {
                correct?.let {cor ->
                    submitFunction(it, cor)
                }
            }
            3 -> optionFour?.let {
                correct?.let {cor ->
                    submitFunction(it, cor)
                }
            }
        }

    }

    override fun onClick(view: View?) {
        Log.e("pressed view with id:", "${view?.id}")

        when (view?.id){
            R.id.optionOne -> {
                selectedQuestion(view as TextView, 0)
            }
            R.id.optionTwo -> {
                selectedQuestion(view as TextView, 1)
            }
            R.id.optionThree -> {
                selectedQuestion(view as TextView, 2)
            }
            R.id.optionFour -> {
                selectedQuestion(view as TextView, 3)
            }
            R.id.submitFlag -> {
                checkAnswer()
            }
        }
    }
}