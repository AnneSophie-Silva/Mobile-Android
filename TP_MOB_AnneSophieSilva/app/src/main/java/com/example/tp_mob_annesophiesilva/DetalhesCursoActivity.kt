package com.example.tp_mob_annesophiesilva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.tp_mob_annesophiesilva.databinding.ActivityDetalhesCursoBinding

class DetalhesCursoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesCursoBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var imagemId: Int = -1
    private var curso = Curso()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesCursoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.getIntExtra("id", 0)
        dbHelper = DBHelper(applicationContext)


        if (id != null) {
            curso = dbHelper.selectCursoByIdObjeto(id)
            populate()
        } else {
            finish()
        }

        binding.buttonEditar.setOnClickListener {
            binding.layoutEditarEliminar.visibility = View.VISIBLE
            binding.layoutEditar.visibility = View.GONE
            changeEditText(true)

        }

        binding.buttonCancelar.setOnClickListener {
            binding.layoutEditarEliminar.visibility = View.GONE
            binding.layoutEditar.visibility = View.VISIBLE
            populate()
            changeEditText(false)
        }

        binding.buttonSalvar.setOnClickListener {
            val novaImagem = if (imagemId > 0) imagemId else curso.imagemId
            val res = dbHelper.updateCursos(
                id = curso.id,
                nome = binding.editNome.text.toString(),
                local = binding.editLocal.text.toString(),
                dataInicio = binding.editDataInicio.text.toString(),
                dataFim = binding.editDataFim.text.toString(),
                preco = binding.editPreco.text.toString().toDouble(),
                duracao = binding.editDuracao.text.toString().toInt(),
                edicao = binding.editEdicao.text.toString().toInt(),
                imagemId = novaImagem)

            if (res > 0) {
                Toast.makeText(applicationContext, "As alterações foram guardadas", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Erro no update", Toast.LENGTH_SHORT).show()
                setResult(0, i)
                finish()
            }
        }

        binding.buttonApagar.setOnClickListener {
            val res = dbHelper.deleteCursos(id)
            if (res > 0) {
                Toast.makeText(applicationContext, "Curso apagado com sucesso", Toast.LENGTH_SHORT).show()
                    setResult(1, i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Erro ao apagar", Toast.LENGTH_SHORT).show()
                    setResult(0, i)
                    finish()
                }
            }

        binding.imagemCurso.setOnClickListener {
            if (binding.editNome.isEnabled) {
                launcher.launch(Intent(applicationContext, CursoImagemActivity::class.java))
            }
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                if (it.data?.extras!=null){
                    imagemId = it.data?.getIntExtra("id", 0)!!
                    binding.imagemCurso.setImageResource((imagemId!!))
                }


            } else {
                imagemId = -1
                binding.imagemCurso.setImageResource(R.drawable.noimage)
            }
        }

        binding.buttonVoltar.setOnClickListener { finish() }

    }

    private fun changeEditText(status: Boolean) {
        binding.editNome.isEnabled = status
        binding.editLocal.isEnabled = status
        binding.editDataInicio.isEnabled = status
        binding.editDataFim.isEnabled = status
        binding.editPreco.isEnabled = status
        binding.editDuracao.isEnabled = status
        binding.editEdicao.isEnabled = status
    }

    private fun populate() {
        binding.editNome.setText(curso.nome)
        binding.editLocal.setText(curso.local)
        binding.editDataInicio.setText(curso.dataInicio)
        binding.editDataFim.setText(curso.dataFim)
        binding.editPreco.setText(curso.preco.toString())
        binding.editDuracao.setText(curso.duracao.toString())
        binding.editEdicao.setText(curso.edicao.toString())
        if (curso.imagemId > 0) {
            binding.imagemCurso.setImageResource(curso.imagemId)
        } else {
            binding.imagemCurso.setImageResource(R.drawable.noimage)
        }
    }


}

