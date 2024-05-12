package com.example.mylotteryapp.resultados.modelos.euroDreams

import kotlinx.serialization.Serializable

@Serializable
data class Documento(
    val tituloRelacion: String?,
    val tituloContenido: String,
    val urlContenido: String
)