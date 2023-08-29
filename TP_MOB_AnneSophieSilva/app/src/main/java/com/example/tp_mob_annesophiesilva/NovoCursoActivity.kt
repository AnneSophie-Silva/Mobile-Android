package com.example.tp_mob_annesophiesilva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.tp_mob_annesophiesilva.databinding.ActivityNovoCursoBinding

class NovoCursoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovoCursoBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var id: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovoCursoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = DBHelper(this)
        val i = intent

        binding.buttonSalvar.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val local = binding.editLocal.text.toString()
            val dataInicio = binding.editDataInicio.text.toString()
            val dataFim = binding.editDataFim.text.toString()
            val preco = binding.editPreco.text.toString().toDoubleOrNull()
            val duracao = binding.editDuracao.text.toString().toIntOrNull()
            val edicao = binding.editEdicao.text.toString().toIntOrNull()
            var imagemId = -1
            if (id != null ) {
                imagemId = id as Int
            }

            if (nome.isNotEmpty() && local.isNotEmpty() && dataInicio.isNotEmpty() && dataFim.isNotEmpty() && preco != null && duracao != null && edicao != null && imagemId > 0) {
                val res = dbHelper.insertCurso(nome, local, dataInicio, dataFim, preco, duracao, edicao, imagemId)
                if (res > 0) {
                    Toast.makeText(applicationContext, "O novo curso foi inserido", Toast.LENGTH_SHORT).show()
                    setResult(1, i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Erro ao inserir o curso", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Preencha todos os campos (incluindo uma imagem)", Toast.LENGTH_SHORT).show()
            }
        }


        binding.buttonCancelar.setOnClickListener {
            setResult(0, i)
            finish()
        }

        binding.imagemCurso.setOnClickListener{
            launcher.launch(Intent(applicationContext, CursoImagemActivity::class.java))
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.data!= null && it.resultCode == 1){
                id = it.data?.extras?.getInt("id")
                binding.imagemCurso.setImageResource(id!!)
            }else{
                id = -1
                binding.imagemCurso.setImageResource(R.drawable.noimage)
            }
        }

    }
}