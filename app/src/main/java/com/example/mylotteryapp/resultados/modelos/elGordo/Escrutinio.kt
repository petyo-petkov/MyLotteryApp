package com.example.mylotteryapp.resultados.modelos.elGordo

import kotlinx.serialization.Serializable

@Serializable
data class Escrutinio(
    val tipo: String,
    val categoria: Int,
    val premio: String,
    val ganadores: String
)