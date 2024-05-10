package com.example.mylotteryapp.getInfoLoterias.modelos.euromillones

import kotlinx.serialization.Serializable

@Serializable
data class Millon(
    val activo: String? = null,
    val combinacion: String? = null,
    val gameid: String? = null,
    val importe: Int? = null,
    val relsorteoid_asociado: String? = null
)