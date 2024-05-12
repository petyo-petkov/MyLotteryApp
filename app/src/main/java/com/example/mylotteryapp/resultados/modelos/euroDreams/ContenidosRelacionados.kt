package com.example.mylotteryapp.resultados.modelos.euroDreams

import kotlinx.serialization.Serializable

@Serializable
data class ContenidosRelacionados(
    val noticias: List<Noticia>,
    val enlaces: List<String>,
    val puntosDeVenta: List<String>,
    val documentos: List<Documento>,
    val imagenes: List<String>,
    val preguntasFrecuentes: List<String>,
    val paginasLibres: List<String>
)