package com.example.mylotteryapp.getInfoLoterias.modelos.bonoloto

import kotlinx.serialization.Serializable

@Serializable
data class Escrutinio(
    val categoria: Int,
    val ganadores: String,
    val premio: String,
    val tipo: String
)