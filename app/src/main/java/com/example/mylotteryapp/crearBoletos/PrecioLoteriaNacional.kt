package com.example.mylotteryapp.crearBoletos

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun precioLoteriaNacional(fechaLoteria: String): Double {

    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    val fecha = Date((fechaToRealmInstant(fechaLoteria)).epochSeconds * 1000)
    val fechaFormateada = formatter.format(fecha)

    val calendario = Calendar.getInstance()
    calendario.set(Calendar.YEAR, fechaFormateada.substring(6, 10).toInt())
    calendario.set(Calendar.MONTH, fechaFormateada.substring(3, 5).toInt() - 1)
    calendario.set(Calendar.DAY_OF_MONTH, fechaFormateada.substring(0, 2).toInt())

    val diaSemana = calendario.get(Calendar.DAY_OF_WEEK)
    val dia = calendario.get(Calendar.DAY_OF_MONTH)

    val precio = when (diaSemana) {
        Calendar.THURSDAY -> 3.0
        Calendar.SATURDAY -> {
            if (dia in 15..28) {
                15.0
            } else  { 6.0 }
        }
        else -> { 0.1 }
    }
    return precio
}

