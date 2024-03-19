package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.Boleto
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun crearLoteriaFromBarCode(data: String): Boleto {

    val formatter = SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH)
    val fechaInicio = Calendar.getInstance()
    val serialNumber = data.slice(0..9).toLong()
    val numeroLoteriaNacional = data.slice(11..15)
    val serieLoteriaNacional = data.slice(7..9)
    val sorteoLoteriaNacional = data.slice(2..3).toInt()

    when (data.first().toString()) {

        "6" -> {
            fechaInicio.set(2024, 0, 4)
        }

        "5" -> {
            fechaInicio.set(2024, 0, 6)
        }

    }
    val diasTranscurridos = ((((sorteoLoteriaNacional - 1) / 2)) * 7)
    val fechaSorteo = fechaInicio.apply { add(Calendar.DAY_OF_MONTH, diasTranscurridos) }
    val fechaString = formatter.format(fechaSorteo.time)
    val fechaRealm = fechaToRealmInstant(fechaString)
    val precioLoteria = precioLoteriaNacional(fechaString)

    return Boleto().apply {
        tipo = "Loteria Nacional"
        numeroSerie = serialNumber
        fecha = fechaRealm
        numeroLoteria = numeroLoteriaNacional
        serieLoteria = serieLoteriaNacional
        sorteoLoteria = sorteoLoteriaNacional.toString()
        precio = precioLoteria
        premio = 0.0
    }

}