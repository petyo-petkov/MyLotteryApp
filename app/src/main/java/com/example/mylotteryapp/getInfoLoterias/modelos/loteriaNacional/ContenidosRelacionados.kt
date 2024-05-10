package com.example.mylotteryapp.getInfoLoterias.modelos.loteriaNacional

import kotlinx.serialization.Serializable

@Serializable
data class ContenidosRelacionados(
    val documentos: List<Documento>,
    val enlaces: List<String>,
    val imagenes: List<Imagene>,
    val noticias: List<Noticia>,
    val paginasLibres: List<String>,
    val preguntasFrecuentes: List<String>,
    val puntosDeVenta: List<String>
)