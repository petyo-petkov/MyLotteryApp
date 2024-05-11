package com.example.mylotteryapp.resultados.modelos.loteriaNacional

import kotlinx.serialization.Serializable


@Serializable
data class Imagene(
    val tituloContenido: String? = null,
    val tituloRelacion: String? = null,
    val urlContenido: String? = null
)