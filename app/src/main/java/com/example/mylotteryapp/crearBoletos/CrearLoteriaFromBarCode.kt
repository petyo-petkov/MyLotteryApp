package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.resultados.getInfoByNumSorteo
import io.realm.kotlin.types.RealmInstant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

suspend fun crearLoteriaFromBarCode(data: String): Boleto {

    //val formatter = SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH)
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val calendario = Calendar.getInstance()
    val serialNumber = data.substring(0, 10).toLong()
    val numeroLoteriaNacional = data.substring(11, 16)
    val serieLoteriaNacional = data.substring(7, 10)
    //val numeroSorteo = data.substring(1, 4).toInt()
    val numeroSorteo = data.substring(1, 4)

    /*
    when (data.substring(0, 1)) {
        "6" -> calendario.set(2024, 0, 4) //primer sorteo jueves
        "5" -> calendario.set(2024, 0, 6) //primer sorteo sabado
        else -> throw IllegalArgumentException("Invalid lottery code")
    }

    val diasTranscurridos = ((((numeroSorteo - 1) / 2)) * 7)
    val fechaSorteo = calendario.apply { add(Calendar.DAY_OF_MONTH, diasTranscurridos) }
    val fechaString = when (numeroSorteo) {
        36 -> "05MAY24"
        else -> formatter.format(fechaSorteo.time)
    }

    val fechaRealm = fechaToRealmInstant(fechaString)
    val precioLoteria = precioLoteriaNacional(fechaString)

     */

    val infoLoteria = getInfoByNumSorteo(numeroSorteo)
    val fechaSorteo = infoLoteria.fechaSorteo
    val fechaLong = formatter.parse(fechaSorteo.substring(1..20))!!.time

    return Boleto().apply {
        tipo = "Loteria Nacional"
        numeroSerie = serialNumber
        fecha = RealmInstant.from(fechaLong / 1000, 0)
        numeroLoteria = numeroLoteriaNacional
        serieLoteria = serieLoteriaNacional
        sorteoLoteria = numeroSorteo.toString()
        precio = infoLoteria.precioSorteo.toDouble()
        premio = 0.0
    }

}