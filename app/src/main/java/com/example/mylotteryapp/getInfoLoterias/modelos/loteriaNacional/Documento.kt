package com.example.mylotteryapp.getInfoLoterias.modelos.loteriaNacional

import kotlinx.serialization.Serializable

@Serializable
data class Documento(
    val tituloContenido: String? = null,
    val tituloRelacion: String? = null,
    val urlContenido: String? = null
)