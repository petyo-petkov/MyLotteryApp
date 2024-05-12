package com.example.mylotteryapp.resultados.modelos.primitva

import kotlinx.serialization.Serializable

@Serializable
data class Joker(
    val activo: String,
    val bote_joker: Int,
    val combinacion: String,
    val gameid: String,
    val relsorteoid_asociado: String
)