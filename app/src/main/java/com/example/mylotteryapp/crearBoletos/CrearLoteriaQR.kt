package com.example.mylotteryapp.crearBoletos

import android.util.Log
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.models.LoteriaNacional
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

suspend fun crearLoteriaQR(data: String, realm: Realm, realmRepo: RealmRepository) {


    val info = data.split(";")
    val serialNumber = info[0].substringAfter("=").takeLast(10).toLong()
    val fechaString = info[2].substringAfter("=").slice(3..9)
    val fechaRealm = fechaToRealmInstant(fechaString)
    val numeroLoteria = info[4].substringAfter("=")
    val serieLoteria = info[6].substringAfter("=")
    val sorteoLoteria = info[2].substringAfter("=").slice(0..2).toInt()

    val precioLoteria = precioLoteriaNacional(fechaString)

     val loteria = LoteriaNacional().apply {
        numeroSerie = serialNumber
        fecha = fechaRealm
        numero = numeroLoteria
        serie = serieLoteria
        sorteo = sorteoLoteria
        precio = precioLoteria
        premio = 0.0
        esPremiado = null
    }
    Boletos().apply { loterias?.add(loteria) }
    val result = realm.query<LoteriaNacional>("numeroSerie==$0", loteria.numeroSerie).find()

    if (result.isEmpty()) {
        val boleto = Boletos().apply {
            fechaBoleto = loteria.fecha
            loterias?.add(loteria)
        }
        realmRepo.insertarBoleto(boleto)
    } else {
        Log.i("loteria", "ya existe boleto")
    }

}


