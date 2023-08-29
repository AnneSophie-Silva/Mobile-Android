package com.example.tp_mob_annesophiesilva.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mob_annesophiesilva.Curso
import com.example.tp_mob_annesophiesilva.R
import com.example.tp_mob_annesophiesilva.adapter.listener.CursoOnClickListener
import com.example.tp_mob_annesophiesilva.adapter.viewholder.CursoViewHolder

class CursoListAdapter(private val cursoLista: List<Curso>,
                       private val cursoOnClickListener: CursoOnClickListener
                       ) : RecyclerView.Adapter<CursoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_curso, parent, false)
        return CursoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cursoLista.size
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = cursoLista[position]
        holder.textInformation.text = "${curso.nome} | ${curso.local} - ${curso.dataInicio} "
        if (curso.imagemId > 0) {
            holder.imagem.setImageResource(curso.imagemId)
        } else {
            holder.imagem.setImageResource(R.drawable.noimage)
        }
        holder.itemView.setOnClickListener{
            cursoOnClickListener.clickListener(curso)
        }
    }

}