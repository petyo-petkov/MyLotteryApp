package com.example.mylotteryapp.resultados.modelos.primitva

import kotlinx.serialization.Serializable

@Serializable
data class ResultadosPrimitiva(
    val fecha_sorteo: String,
    val combinacion: String,
    val apuestas: String,
    val cdc: String,
    val combinacion_acta: String,
    val contenidosRelacionados: ContenidosRelacionados,
    val dia_semana: String,
    val escrutinio: List<Escrutinio>,
    val escrutinio_joker: List<EscrutinioJoker>,
    val anyo: String,
    val fondo_bote: String,
    val game_id: String,
    val id_sorteo: String,
    val joker: Joker,
    val numero: Int,
    val premio_bote: String,
    val premios: String,
    val recaudacion: String
)