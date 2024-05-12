package com.example.mylotteryapp.resultados.modelos.elGordo

import kotlinx.serialization.Serializable

@Serializable
data class ElGordo(
    val fecha_sorteo: String,
    val dia_semana: String,
    val id_sorteo: String,
    val game_id: String,
    val anyo: String,
    val numero: Int,
    val premio_bote: String,
    val cdc: String,
    val apuestas: String,
    val recaudacion: String,
    val combinacion: String,
    val combinacion_acta: String,
    val premios: String,
    val fondo_bote: String,
    val escrutinio: List<Escrutinio>,
    val contenidosRelacionados: ContenidosRelacionados
)