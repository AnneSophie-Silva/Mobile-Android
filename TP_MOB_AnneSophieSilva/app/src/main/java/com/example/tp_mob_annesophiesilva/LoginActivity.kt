package com.example.tp_mob_annesophiesilva

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tp_mob_annesophiesilva.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = DBHelper(this)

        binding.buttonSobre.setOnClickListener {
            val i = Intent(this, SobreActivity::class.java)
            startActivity(i)
        }

        binding.buttonEntrar.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()

            val administrador1 = dbHelper.loginAdministrador("adm1", "pass1")
            val administrador2 = dbHelper.loginAdministrador("adm2", "pass2")
            val administrador3 = dbHelper.loginAdministrador("adm3", "pass3")

            val administrador = when (username) {
                "adm1" -> administrador1
                "adm2" -> administrador2
                "adm3" -> administrador3
                else -> null
            }

            if (administrador?.username == username && administrador?.password == password) {
                Toast.makeText(applicationContext, "Login válido", Toast.LENGTH_SHORT).show()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(applicationContext, "Login inválido", Toast.LENGTH_SHORT).show()
            }

            binding.editUsername.setText("")
            binding.editPassword.setText("")

            val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.login)
            mediaPlayer.start()
        }
    }
}