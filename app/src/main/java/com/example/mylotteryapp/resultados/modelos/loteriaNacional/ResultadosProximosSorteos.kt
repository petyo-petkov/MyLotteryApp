package com.example.mylotteryapp.resultados.modelos.loteriaNacional

import kotlinx.serialization.Serializable

@Serializable
data class ResultadosProximosSorteos(
    val fecha: String,
    val dia_semana: String,
    val id_sorteo: String,
    val game_id: String,
    val apertura: String,
    val cierre: String,
    val anyo: String,
    val nombre: String,
    val lugar: String,
    val precio: Int,
    val premio_bote: Long?,
    val destacar_bote: String,
    val cdc: String,
    val recaudacion: Long?,
    val estado: String,
    val premio_especial: Long?,
    val primer_premio: Int
)