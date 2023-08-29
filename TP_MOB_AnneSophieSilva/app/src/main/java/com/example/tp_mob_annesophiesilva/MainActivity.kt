package com.example.tp_mob_annesophiesilva

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tp_mob_annesophiesilva.adapter.CursoListAdapter
import com.example.tp_mob_annesophiesilva.adapter.listener.CursoOnClickListener
import com.example.tp_mob_annesophiesilva.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listaCursos: ArrayList<Curso>
    //private lateinit var adapter: ArrayAdapter<Curso>
    private lateinit var adapter: CursoListAdapter
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        listaCursos = dbHelper.selectAllCursoLista()

        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        binding.recyclerViewCursos.layoutManager = LinearLayoutManager(applicationContext)
        loadList()

        binding.buttonLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.logout)
            mediaPlayer.start()
            finish()
        }



        binding.buttonNovoCurso.setOnClickListener {
            result.launch(Intent(applicationContext, NovoCursoActivity::class.java))
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.data!=null && it.resultCode == 1){
                loadList()
            }else if (it.data!=null && it.resultCode == 0) {
                Toast.makeText(applicationContext, "Operação cancelada", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun loadList() {

        listaCursos = dbHelper.selectAllCursoLista()
        adapter = CursoListAdapter(listaCursos, CursoOnClickListener { curso ->
            val intent = Intent(this, DetalhesCursoActivity::class.java)
            intent.putExtra("id", curso.id)
            result.launch(intent)
        })
        binding.recyclerViewCursos.adapter = adapter

        binding.buttonOrdenarNome.setOnClickListener {
            listaCursos.sortBy { it.nome }
            adapter.notifyDataSetChanged()
        }

        binding.buttonOrdenarData.setOnClickListener {
            listaCursos.sortBy { it.dataInicio }
            adapter.notifyDataSetChanged()
        }

        binding.buttonOrdenarNomeDec.setOnClickListener {
            listaCursos.sortByDescending { it.nome }
            adapter.notifyDataSetChanged()
        }

        binding.buttonOrdenarDataDec.setOnClickListener {
            listaCursos.sortByDescending { it.dataInicio }
            adapter.notifyDataSetChanged()
        }

    }
}