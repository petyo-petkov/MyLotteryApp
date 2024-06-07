package com.example.mylotteryapp.resultados.modelos.euromillones.proximos

import kotlinx.serialization.Serializable

@Serializable
data class Millon(
    val gameid: String,
    val relsorteoid_asociado: String,
    val importe: Int,
    val activo: String
)