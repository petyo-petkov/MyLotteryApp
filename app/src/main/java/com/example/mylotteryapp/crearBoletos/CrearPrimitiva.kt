package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.Primitiva
import io.realm.kotlin.ext.toRealmList


fun crearPrimitiva(data: String): Primitiva {

    val info = data.split(";")
    val serialNumber = info[0].substringAfter("=").takeLast(10).toLong()
    val fechaString = info[2].substringAfter("=").slice(3..9)
    val fechaRealm = fechaToRealmInstant(fechaString)
    val numeroSorteosJugados = info[2].substringAfter("=").last().digitToInt()
    val numReintegro = info[6].substringAfter("=")
    val partesCombinaciones = info[4].split(".").drop(1)
    val jokerPrimitiva: String = info[7].substringAfter("=")

    val combinacionesJugadas = mutableListOf<String>()
    combinacionesJugadas.addAll(partesCombinaciones.map {
        it.substringAfter("=").chunked(2).joinToString(" ") })

    val precioPrimitiva: Double = if (jokerPrimitiva != "NO") {
        (((combinacionesJugadas.size * 1.0) + 1) * numeroSorteosJugados)
    } else {
        ((combinacionesJugadas.size * 1.0) * numeroSorteosJugados)
    }

    val primitiva = Primitiva().apply {
        numeroSerie = serialNumber
        fecha = fechaRealm
        combinaciones = combinacionesJugadas.toRealmList()
        reintegro = numReintegro
        precio = precioPrimitiva
        premio = 0.0
        joker = jokerPrimitiva

    }
    return primitiva
}