package com.example.mylotteryapp.crearBoletos

import java.text.SimpleDateFormat
import java.util.Locale

fun fechaToLong(date: String): Long {
    val meses = mapOf(
        "ENE" to "JAN",
        "FEB" to "FEB",
        "MAR" to "MAR",
        "ABR" to "APR",
        "MAY" to "MAY",
        "JUN" to "JUN",
        "JUL" to "JUL",
        "AGO" to "AUG",
        "SEP" to "SEP",
        "OCT" to "OCT",
        "NOV" to "NOV",
        "DIC" to "DEC"
    )
    val fechaEng = date.replace(Regex("[A-Z]{3}")) {
        meses[it.value] ?: it.value
    }
    val formatter = SimpleDateFormat("ddMMMyy", Locale.ENGLISH)
    val fechaLong = formatter.parse(fechaEng)!!.time

    return fechaLong
}