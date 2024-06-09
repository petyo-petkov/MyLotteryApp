package com.example.mylotteryapp.resultados.modelos.euroDreams.proximos

import kotlinx.serialization.Serializable

@Serializable
data class ResultadoProximosEDMS(
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
    val estado: String
)