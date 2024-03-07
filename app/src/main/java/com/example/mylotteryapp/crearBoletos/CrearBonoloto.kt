package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.Bonoloto
import io.realm.kotlin.ext.toRealmList

fun crearBonoloto(data: String): Bonoloto {

    val info = data.split(";")
    var serialNumber = 0L
    var fechaString = ""
    var numeroSorteosJugados = 0
    val combinacionesJugadas = mutableListOf<String>()
    var numReintegro = 0
    val precioBonoloto: Double

    val partesCombinaciones = info[4].split(".").drop(1)
    partesCombinaciones.forEach { parte ->
        combinacionesJugadas.add(convertirString(parte))
    }

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

                "R" -> numReintegro = value.toInt()
            }
        }
    }
    precioBonoloto = ((combinacionesJugadas.size * 0.5) * numeroSorteosJugados)

    val bonoloto = Bonoloto().apply {
        numeroSerie = serialNumber
        fecha = fechaToRealmInstant(fechaString)
        combinaciones = combinacionesJugadas.toRealmList()
        reintegro = numReintegro
        precio = precioBonoloto
        premio = null

    }
    return bonoloto

}

// Convierte "1=1234567890" en "12 34 56 78 90"
private fun convertirString(input: String): String {
    val regex = Regex("(\\d{2})")
    val matches = regex.findAll(input)
    val numeros = mutableListOf<String>()
    for (match in matches) {
        numeros.add(match.value)
    }

    return numeros.joinToString(" ")
}
