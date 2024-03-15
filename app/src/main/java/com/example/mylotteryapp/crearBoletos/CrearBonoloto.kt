package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.Bonoloto
import io.realm.kotlin.ext.toRealmList

fun crearBonoloto(data: String): Bonoloto {

    val info = data.split(";")
    val serialNumber = info[0].substringAfter("=").takeLast(10).toLong()
    val fechaString = info[2].substringAfter('=').slice(3..9)
    val fechaRealm = fechaToRealmInstant(fechaString)
    val numeroSorteosJugados = info[2].substringAfter('=').last().digitToInt()
    val numReintegro = info[6].substringAfter("=")
    val partesCombinaciones = info[4].split(".").drop(1)

    val combinacionesJugadas = mutableListOf<String>()
    combinacionesJugadas.addAll(partesCombinaciones.map {
        it.substringAfter("=").chunked(2).joinToString(" ") })

    val precioBonoloto: Double = ((combinacionesJugadas.size * 0.5) * numeroSorteosJugados)

    val bonoloto = Bonoloto().apply {
        numeroSerie = serialNumber
        fecha = fechaRealm
        combinaciones = combinacionesJugadas.toRealmList()
        reintegro = numReintegro
        precio = precioBonoloto
        premio = 0.0

    }
    return bonoloto

}
