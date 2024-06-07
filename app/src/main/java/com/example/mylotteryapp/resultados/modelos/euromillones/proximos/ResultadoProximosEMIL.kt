package com.example.mylotteryapp.resultados.modelos.euromillones.proximos

import kotlinx.serialization.Serializable

@Serializable
data class ResultadoProximosEMIL(
    val fecha: String,
    val dia_semana: String,
    val id_sorteo: String,
    val game_id: String,
    val apertura: String,
    val cierre: String,
    val anyo: String,
    val premio_bote: String?,
    val destacar_bote: String,
    val cdc: String,
    val recaudacion: String?,
    val millon: Millon,
    val lluvia: String?,
    val estado: String
)