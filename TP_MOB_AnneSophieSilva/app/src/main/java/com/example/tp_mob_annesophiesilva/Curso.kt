package com.example.tp_mob_annesophiesilva

data class Curso (
    val id: Int = 0,
    val nome: String = "",
    val local: String = "",
    val dataInicio: String = "",
    val dataFim: String = "",
    val preco: Double = 0.0,
    val duracao: Int = 0,
    val edicao: Int = 0,
    val imagemId: Int = 0,) {

    override fun toString(): String {
        return "${nome} | ${local} - ${dataInicio}"
    }
}