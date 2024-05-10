package com.example.mylotteryapp.getInfoLoterias.modelos.euromillones

import kotlinx.serialization.Serializable

@Serializable
data class Escrutinio(
    val categoria: Int? = null,
    val ganadores: String? = null,
    val ganadores_eu: String? = null,
    val premio: String? = null,
    val tipo: String? = null
)