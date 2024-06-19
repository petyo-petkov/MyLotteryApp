package com.example.mylotteryapp.resultados.modelos.euromillones

import kotlinx.serialization.Serializable

@Serializable
data class Escrutinio(
    val categoria: Int,
    val ganadores: String,
    val ganadores_eu: String,
    val premio: String,
    val tipo: String
)