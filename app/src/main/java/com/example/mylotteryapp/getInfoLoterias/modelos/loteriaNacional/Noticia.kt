package com.example.mylotteryapp.getInfoLoterias.modelos.loteriaNacional

import kotlinx.serialization.Serializable

@Serializable
data class Noticia(
    val tituloContenido: String,
    val tituloRelacion: String,
    val urlContenido: String
)