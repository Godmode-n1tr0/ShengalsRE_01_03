package com.bignerdranch.android.raschet_ploshad

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView

class Result : AppCompatActivity() {

    private lateinit var textViewFinalResult: TextView
    private lateinit var buttonRegistration: Button

    @SuppressLint("WearRecents")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        textViewFinalResult = findViewById(R.id.textViewFinalResult)
        buttonRegistration = findViewById(R.id.buttonRegistration)

        val result = intent.getStringExtra("result")
        if (!result.isNullOrEmpty()) {
            textViewFinalResult.text = result
        }

        buttonRegistration.setOnClickListener {
            val intent = Intent(this, flat_bank::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}