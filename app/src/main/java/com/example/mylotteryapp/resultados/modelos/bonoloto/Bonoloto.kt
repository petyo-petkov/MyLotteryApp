package com.example.mylotteryapp.resultados.modelos.bonoloto

import kotlinx.serialization.Serializable

@Serializable
data class Bonoloto(
    val anyo: String,
    val apuestas: String,
    val cdc: String,
    val combinacion: String,
    val combinacion_acta: String,
    val contenidosRelacionados: ContenidosRelacionados,
    val dia_semana: String,
    val escrutinio: List<Escrutinio>,
    val fecha_sorteo: String,
    val fondo_bote: String,
    val game_id: String,
    val id_sorteo: String,
    val numero: Int,
    val premio_bote: String,
    val premios: String,
    val recaudacion: String
)