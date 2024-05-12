package com.example.mylotteryapp.resultados.modelos.primitva

import kotlinx.serialization.Serializable

@Serializable
data class Escrutinio(
    val categoria: Int,
    val ganadores: String,
    val premio: String,
    val tipo: String
)