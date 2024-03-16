package com.example.mylotteryapp.crearBoletos

import android.util.Log
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.models.Primitiva
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList


suspend fun crearPrimitiva(data: String, realm: Realm, realmRepo: RealmRepository) {

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
        it.substringAfter("=").chunked(2).joinToString(" ")
    })

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
    Boletos().apply { primitivas?.add(primitiva) }
    val result = realm.query<Primitiva>("numeroSerie==$0", primitiva.numeroSerie).find()

    if (result.isEmpty()) {
        val boleto = Boletos().apply {
            fechaBoleto = primitiva.fecha
            primitivas?.add(primitiva)
        }
        realmRepo.insertarBoleto(boleto)
    } else {
        Log.i("primitiva", "ya existe primitiva")
    }

}
