package com.example.mylotteryapp.resultados.modelos.euroDreams

import kotlinx.serialization.Serializable

@Serializable
data class Escrutinio(
    val tipo: String,
    val categoria: Int,
    val premio: String,
    val ganadores: String,
    val ganadores_eu: String,
    val num_pagos: Int,
    val periodicidad: String?,
    val cantidad_periodica: String?
)