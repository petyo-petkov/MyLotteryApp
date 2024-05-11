package com.example.mylotteryapp.resultados.modelos.euromillones

import kotlinx.serialization.Serializable

@Serializable
data class Noticia(
    val tituloContenido: String? = null,
    val tituloRelacion: String? = null,
    val urlContenido: String? = null
)