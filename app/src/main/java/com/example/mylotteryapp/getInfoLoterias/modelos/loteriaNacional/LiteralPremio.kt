package com.example.mylotteryapp.getInfoLoterias.modelos.loteriaNacional

import kotlinx.serialization.Serializable

@Serializable
data class LiteralPremio(
    val ca: String,
    val en: String,
    val es: String,
    val eu: String,
    val gl: String,
    val va: String
)