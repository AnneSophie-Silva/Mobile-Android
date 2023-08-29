package com.example.tp_mob_annesophiesilva.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mob_annesophiesilva.R

class CursoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val imagem: ImageView = view.findViewById(R.id.imagemCurso)
    val textInformation: TextView = view.findViewById(R.id.cursoInformation)
}