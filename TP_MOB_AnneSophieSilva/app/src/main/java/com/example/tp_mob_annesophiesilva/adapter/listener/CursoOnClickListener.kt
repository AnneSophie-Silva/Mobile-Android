package com.example.tp_mob_annesophiesilva.adapter.listener

import com.example.tp_mob_annesophiesilva.Curso

class CursoOnClickListener(val clickListener: (curso: Curso) -> Unit) {
    fun OnClick(curso: Curso) = clickListener
}