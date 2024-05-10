package com.example.mylotteryapp.getInfoLoterias.modelos.euromillones

import kotlinx.serialization.Serializable

@Serializable
data class Euromillones(
    val fecha_sorteo: String,
    val combinacion: String,
    val anyo: String,
    val apuestas: String,
    val cdc: String,
    val combinacion_acta: String,
    val contenidosRelacionados: ContenidosRelacionados,
    val dia_semana: String,
    val escrutinio: List<Escrutinio>,
    val escrutinio_lluvia: String? = null,
    val escrutinio_millon: List<EscrutinioMillon>,
    val fondo_bote: String,
    val game_id: String,
    val id_sorteo: String,
    val lluvia: String? = null,
    val millon: Millon,
    val numero: Int,
    val premio_bote: String,
    val premios: String,
    val recaudacion: String,
    val recaudacion_europea: String
)