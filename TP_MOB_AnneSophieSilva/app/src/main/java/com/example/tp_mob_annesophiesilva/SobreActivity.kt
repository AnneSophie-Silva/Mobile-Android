package com.example.tp_mob_annesophiesilva

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tp_mob_annesophiesilva.databinding.ActivitySobreBinding

class SobreActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySobreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySobreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonVoltar.setOnClickListener { finish() }
    }
}