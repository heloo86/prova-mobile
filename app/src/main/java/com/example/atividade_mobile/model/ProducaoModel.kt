package com.example.atividade_mobile.model

data class ProducaoModel(
    val operador: String,
    val linha: String,
    val pecas: Int,
    val tempoTotalSegundos: Long,
    val taktTime: Double
)
