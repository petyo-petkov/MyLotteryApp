package com.example.mylotteryapp.getInfoLoterias.modelos.loteriaNacional

import kotlinx.serialization.Serializable

@Serializable
data class Escrutinio(
    val categoria: Int,
    val categoriaLinea: Int,
    val esPremioMayor: Boolean,
    val ganadores: Int,
    val literalPremio: LiteralPremio,
    val mostrarCategoria: Boolean,
    val ordenCategoria: Int,
    val premio: String,
    val resultado: Int,
    val tipo: String
)