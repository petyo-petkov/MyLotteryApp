package com.example.mylotteryapp.crearBoletos

import android.util.Log
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.models.EuroMillones
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList

suspend fun crearEuromillones(data: String, realm: Realm, realmRepo: RealmRepository) {

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

    val euromillon = EuroMillones().apply {
        numeroSerie = serialNumber
        fecha = fechaRealm
        combinaciones = combinacionesJugadas.toRealmList()
        estrellas = stars.toRealmList()
        elMillon = numeroMillon
        precio = precioEuromillones
        premio = 0.0

    }
    Boletos().apply { euroMillones?.add(euromillon) }
    val result = realm.query<EuroMillones>("numeroSerie==$0", euromillon.numeroSerie).find()

    if (result.isEmpty()) {
        val boleto = Boletos().apply {
            fechaBoleto = euromillon.fecha
            euroMillones?.add(euromillon)
        }
        realmRepo.insertarBoleto(boleto)
    } else {
        Log.i("primitiva", "ya existe primitiva")
    }

}