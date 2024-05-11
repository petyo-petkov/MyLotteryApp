package com.example.mylotteryapp.resultados.modelos.bonoloto

import kotlinx.serialization.Serializable

@Serializable
data class Noticia(
    val tituloContenido: String,
    val tituloRelacion: String,
    val urlContenido: String
)