package com.example.mylotteryapp.resultados.modelos.primitva.proximos

import kotlinx.serialization.Serializable

@Serializable
data class Joker(
    val gameid: String,
    val relsorteoid_asociado: String,
    val bote_joker: Int,
    val activo: String
)