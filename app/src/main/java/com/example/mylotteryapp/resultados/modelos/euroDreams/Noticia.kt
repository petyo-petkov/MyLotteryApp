package com.example.mylotteryapp.resultados.modelos.euroDreams

import kotlinx.serialization.Serializable

@Serializable
data class Noticia(
    val tituloRelacion: String,
    val tituloContenido: String,
    val urlContenido: String
)