package com.bignerdranch.android.raschet_ploshad
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class flat_bank : AppCompatActivity() {

    private lateinit var editTextLogin: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flat_bank)

        editTextLogin = findViewById(R.id.editTextLogin)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val savedLogin = sharedPreferences.getString("login", "")
        val savedPassword = sharedPreferences.getString("password", "")

        if (!savedLogin.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
            editTextLogin.setText(savedLogin)
            editTextPassword.setText(savedPassword)
        }

        buttonLogin.setOnClickListener {
            val login = editTextLogin.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                showAlertDialog("Введите логин и пароль")
                return@setOnClickListener
            }

            if (login == "Roman" && password == "123") {
                val editor = sharedPreferences.edit()
                editor.putString("login", login)
                editor.putString("password", password)
                editor.apply()

                val intent = Intent(this, Rashet::class.java)
                startActivity(intent)
            } else {
                val savedLogin = sharedPreferences.getString("login", "")
                val savedPassword = sharedPreferences.getString("password", "")

                if (login == savedLogin && password == savedPassword) {
                    val intent = Intent(this, Rashet::class.java)
                    startActivity(intent)
                } else {
                    showAlertDialog("Неверный логин или пароль, правильно: L:Roman P:123")
                }
            }
        }
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Ошибка")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}