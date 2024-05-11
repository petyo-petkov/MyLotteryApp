package com.example.mylotteryapp.resultados.modelos.loteriaNacional

import kotlinx.serialization.Serializable

@Serializable
data class CuartoPremio(
    val alambre: String? = null,
    val decimo: String,
    val fila: Int,
    val literalPremio: LiteralPremio? = null,
    val orden: Int,
    val ordenFila: Int,
    val prize: Int,
    val prizeType: String,
    val showFolded: Boolean,
    val tabla: String? = null
)
