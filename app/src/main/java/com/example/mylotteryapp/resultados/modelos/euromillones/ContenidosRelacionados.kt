package com.example.mylotteryapp.resultados.modelos.euromillones

import kotlinx.serialization.Serializable

@Serializable
data class ContenidosRelacionados(
    val documentos: List<Documento>? = null,
    val enlaces: List<String>? = null,
    val imagenes: List<String>? = null,
    val noticias: List<Noticia>? = null,
    val paginasLibres: List<String>? = null,
    val preguntasFrecuentes: List<String>? = null,
    val puntosDeVenta: List<String>? = null
)