package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.LoteriaNacional
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun crearLoteriaBarCode(data: String): LoteriaNacional {

    val formatter = SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH)
    val fechaInicio = Calendar.getInstance()
    val serialNumber = data.slice(0..9).toLong()
    val numeroLoteria = data.slice(11..15)
    val serieLoteria = data.slice(7..9)
    val sorteoLoteria = data.slice(2..3).toInt()

    when (data.first().toString()) {

        "6" -> {
            fechaInicio.set(2024, 0, 4)
        }

        "5" -> {
            fechaInicio.set(2024, 0, 6)
        }

    }
    val diasTranscurridos = ((((sorteoLoteria - 1) / 2)) * 7)
    val fechaSorteo = fechaInicio.apply { add(Calendar.DAY_OF_MONTH, diasTranscurridos) }
    val fechaString = formatter.format(fechaSorteo.time)
    val fechaRealm = fechaToRealmInstant(fechaString)
    val fechaLoteria = precioLoteriaNacional(fechaString)

    return LoteriaNacional().apply {
        numeroSerie = serialNumber
        fecha = fechaRealm
        numero = numeroLoteria
        serie = serieLoteria
        sorteo = sorteoLoteria
        precio = fechaLoteria
        premio = 0.0
        esPremiado = null
    }
}