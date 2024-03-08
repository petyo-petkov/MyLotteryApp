package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.ElGordo
import io.realm.kotlin.ext.toRealmList

fun crearElGordo(data: String): ElGordo {

    val info = data.split(";")
    val serialNumber = info[0].substringAfter("=").takeLast(10).toLong()
    val fechaString = info[2].substringAfter("=").slice(3..9)
    val fechaRealm = fechaToRealmInstant(fechaString)
    val numeroSorteosJugados = info[2].substringAfter("=").last().digitToInt()
    val partesCombinaciones = info[4].split(".").drop(1)
    val clave = partesCombinaciones.map {
        it.substringAfter(":").chunked(2).joinToString(" ")
    }
    val combinacionesJugadas = mutableListOf<String>()
    combinacionesJugadas.addAll(partesCombinaciones.map {
        it.substringAfter("=").chunked(2).dropLast(2).joinToString(" ")
    })
    val precioEuromillones: Double = ((combinacionesJugadas.size * 1.5) * numeroSorteosJugados)

    val elGordo = ElGordo().apply {
        numeroSerie = serialNumber
        fecha = fechaRealm
        combinaciones = combinacionesJugadas.toRealmList()
        numeroClave = clave.toRealmList()
        precio = precioEuromillones
        premio = 0.0

    }
    return elGordo
}