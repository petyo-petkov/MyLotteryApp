package com.example.mylotteryapp.getInfoLoterias.modelos.loteriaNacional

import kotlinx.serialization.Serializable

@Serializable
data class ExtraccionesDeDosCifra(
    val alambre: String? = null,
    val decimo: String,
    val fila: Int,
    val literalPremio: String? = null,
    val orden: Int,
    val ordenFila: Int,
    val prize: Int,
    val prizeType: String,
    val showFolded: Boolean,
    val tabla: String? = null
)