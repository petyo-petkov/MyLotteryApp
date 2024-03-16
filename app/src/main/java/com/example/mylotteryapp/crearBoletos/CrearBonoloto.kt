package com.example.mylotteryapp.crearBoletos

import android.util.Log
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.models.Bonoloto
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList

suspend fun crearBonoloto(data: String, realm: Realm, realmRepo: RealmRepository) {

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
    Boletos().apply { bonolotos?.add(bonoloto) }
    val result = realm.query<Bonoloto>("numeroSerie==$0", bonoloto.numeroSerie).find()

    if (result.isEmpty()) {
        val boleto = Boletos().apply {
            fechaBoleto = bonoloto.fecha
            bonolotos?.add(bonoloto)
        }
        realmRepo.insertarBoleto(boleto)
    } else {
        Log.i("primitiva", "ya existe primitiva")
    }

}
