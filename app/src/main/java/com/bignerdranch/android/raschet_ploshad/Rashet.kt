package com.bignerdranch.android.raschet_ploshad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class Rashet : AppCompatActivity() {

    private lateinit var spinnerApartmentType: Spinner
    private lateinit var editTextMeters: EditText
    private lateinit var buttonCalculate: Button

    private var selectedApartmentType: String = ""
    private val BASE_COST_PER_METER = 100000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rashet)

        spinnerApartmentType = findViewById(R.id.spinnerApartmentType)
        editTextMeters = findViewById(R.id.editTextMeters)
        buttonCalculate = findViewById(R.id.buttonCalculate)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.apartment_types,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerApartmentType.adapter = adapter

        spinnerApartmentType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedApartmentType = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedApartmentType = ""
            }
        }

        buttonCalculate.setOnClickListener {
            calculateCost()
        }
    }

    private fun calculateCost() {
        if (selectedApartmentType.isEmpty()) {
            Toast.makeText(this, "Выберите тип квартиры", Toast.LENGTH_SHORT).show()
            return
        }

        val metersText = editTextMeters.text.toString().trim()
        if (metersText.isEmpty()) {
            Toast.makeText(this, "Введите количество метров", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val meters = metersText.toDouble()
            val cost = calculateApartmentCost(selectedApartmentType, meters)

            // Переход на третий экран с результатом
            val intent = Intent(this, Result::class.java)
            intent.putExtra("result", "%,.0f тыс. рублей".format(cost))
            startActivity(intent)

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Введите корректное число метров", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateApartmentCost(type: String, meters: Double): Double {
        val multiplier = when (type) {
            "1-о комнатная квартира" -> 1.4
            "2-х комнатная квартира" -> 1.0
            "3-х комнатная квартира" -> 0.8
            "Студия" -> 1.1
            else -> 1.0
        }

        return BASE_COST_PER_METER * meters * multiplier / 1000
    }

    fun onBackClick(view: View) {
        val intent = Intent(this, flat_bank::class.java)
        startActivity(intent)
        finish()
    }
}