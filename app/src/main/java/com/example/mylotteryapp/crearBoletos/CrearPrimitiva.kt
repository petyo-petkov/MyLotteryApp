package com.example.mylotteryapp.crearBoletos

import android.util.Log
import com.example.mylotteryapp.models.Primitiva
import io.realm.kotlin.ext.toRealmList
import java.math.BigInteger


fun crearPrimitiva(data: String): Primitiva {

    val info = data.split(";")
    var serialNumber = 0L
    var fechaString = ""
    var numeroSorteosJugados = 0
    var combinacionesJugadas = mutableListOf<String>()
    var numReintegro = 0
    var precioPrimitiva = 0.0
    var jokerPrimitiva: String? = "NO"

    for (i in info) {
        val parts = i.split("=")
        if (parts.size == 2) {
            val key = parts[0]
            val value = parts[1]
            when (key) {
                "A" -> serialNumber = value.slice(0..9).toLong()
                "S" -> {
                    fechaString = value.slice(3..9)
                    numeroSorteosJugados = value.last().digitToInt()
                }

                ".1" -> {
                    combinacionesJugadas.add(value)
                }

                "R" -> numReintegro = value.toInt()
                "J" -> if (value != "NO") {
                    jokerPrimitiva = value
                }
            }
        }
    }
    precioPrimitiva = if (Primitiva().joker != " NO") {
        (((combinacionesJugadas.size * 1.0) + 1) * numeroSorteosJugados)
    } else {
        ((combinacionesJugadas.size * 1.0) * numeroSorteosJugados)
    }

    val primitiva = Primitiva().apply {
        numeroSerie = serialNumber
        fecha = fechaToRealmInstant(fechaString)
        combinaciones = combinacionesJugadas.toRealmList()
        reintegro = numReintegro
        precio = precioPrimitiva
        premio = null
        joker = jokerPrimitiva

    }
    return primitiva
}
