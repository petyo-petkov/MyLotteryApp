package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.EuroMillones
import io.realm.kotlin.ext.toRealmList

fun crearEuromillones(data: String): EuroMillones {

    val info = data.split(";")
    val serialNumber = info[0].substringAfter("=").takeLast(10).toLong()
    val fechaString = info[2].substringAfter("=").slice(3..9)
    val fechaRealm = fechaToRealmInstant(fechaString)
    val numeroSorteosJugados = info[2].substringAfter("=").last().digitToInt()
    val partesCombinaciones = info[4].split(".").drop(1)
    val stars = partesCombinaciones.map {
        it.substringAfter(":").chunked(2).joinToString(" ")
    }
    val combinacionesJugadas = mutableListOf<String>()
    combinacionesJugadas.addAll(partesCombinaciones.map {
        it.substringAfter("=").chunked(2).dropLast(3).joinToString(" ")
    })
    val numeroMillon = info[6].substringAfter(",").dropLast(1)
    val precioEuromillones: Double = ((combinacionesJugadas.size * 2.5) * numeroSorteosJugados)

    val euromillones = EuroMillones().apply {
        numeroSerie = serialNumber
        fecha = fechaRealm
        combinaciones = combinacionesJugadas.toRealmList()
        estrellas = stars.toRealmList()
        elMillon = numeroMillon
        precio = precioEuromillones
        premio = 0.0

    }
    return euromillones
}