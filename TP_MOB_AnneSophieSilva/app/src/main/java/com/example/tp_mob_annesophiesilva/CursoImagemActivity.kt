package com.example.tp_mob_annesophiesilva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tp_mob_annesophiesilva.databinding.ActivityCursoImagemBinding

class CursoImagemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCursoImagemBinding
    private lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCursoImagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i = intent

        binding.imagem1.setOnClickListener { sendId(R.drawable.frontend) }
        binding.imagem2.setOnClickListener { sendId(R.drawable.dashboard) }
        binding.imagem3.setOnClickListener { sendId(R.drawable.website)}
        binding.imagem4.setOnClickListener { sendId(R.drawable.design) }
        binding.buttonRemoverImagem.setOnClickListener { sendId(R.drawable.noimage) }

    }

    private fun sendId(id:Int) {
        i.putExtra("id", id)
        setResult(1, i)
        finish()
    }
}