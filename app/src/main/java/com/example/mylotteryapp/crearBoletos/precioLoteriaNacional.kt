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
    val mes = calendario.get(Calendar.MONTH)
    var precio = 0.0

    if (diaSemana == Calendar.THURSDAY) {
        precio = 3.0
    }
    if (diaSemana == Calendar.SATURDAY) {
        precio = if (dia in 17..25) {
            15.0
        }else{
            6.0
        }
    }
    if (dia == 6 && mes == 0) {
        precio = 20.0
    }
    if (dia == 22 && mes == 11){
        precio = 20.0
    }

    return precio
}

