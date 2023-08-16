package com.example.myquizzapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input_view:EditText = findViewById(R.id.input_view_text)

        val startButton: Button = findViewById(R.id.go_to_quiz)
        startButton.setOnClickListener{
            if(input_view.text.isEmpty()){
                Toast.makeText(this, "Please Enter your name", Toast.LENGTH_LONG).show()
            }else {
                val intent = Intent(this, quizz_page_activity::class.java)
                intent.putExtra(Constants.USER_NAME, input_view.text)
                startActivity(intent)
                finish()
            }

        }

    }
}