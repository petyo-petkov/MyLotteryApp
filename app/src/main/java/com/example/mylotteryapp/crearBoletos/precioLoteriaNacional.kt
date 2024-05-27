package com.example.mylotteryapp.crearBoletos

import android.icu.util.Calendar
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
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
    val precio: Double

    // Sorteos extraordinarios
    when {
        diaSemana == Calendar.THURSDAY -> precio = 3.0
        dia == 6 && mes == 0 -> precio = 20.0    // El Niño
        dia == 13 && mes == 0 -> precio = 15.0   // Extraordinario de invierno
        dia == 21 && mes == 0 -> precio = 12.0   // Extraordinario de Enero
        dia == 17 && mes == 1 -> precio = 15.0   // Extraordinario de San Valentin
        dia == 23 && mes == 2 -> precio = 15.0   // Extraordinario dia del padre
        dia == 6 && mes == 3 -> precio = 15.0    // Extraordinario AECC
        dia == 5 && mes == 4 -> precio = 15.0    // Extraordinario dia de la madre
        dia == 1 && mes == 5 -> precio = 15.0    // Extraordinario dia de la cruz roja
        dia == 6 && mes == 6 -> precio = 20.0    // Extraordinario Vacaciones
        dia == 13 && mes == 6 -> precio = 15.0   // Extraordinario Julio
        dia == 3 && mes == 7 -> precio = 15.0    // Extraordinario Agosto
        dia == 7 && mes == 8 -> precio = 15.0    // Extraordinario Septiembre

        dia == 22 && mes == 11 -> precio = 20.0
        else -> precio = 6.0
    }

    return precio
}

suspend fun result(): List<JsonObject> {
    val cliente = HttpClient()

    val url = "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=LNAC&num=2"

    val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    return try {
        val response: HttpResponse = cliente.get(url)
        val dataString = response.bodyAsText()
        json.decodeFromString(dataString)
    } catch (e: Exception) {
        Log.e("error resultados", e.message.toString())
        throw e
    }


}

suspend fun foo(id: String): Sorteo {

    val resultados = result()

    var sorteoEncontrado: Sorteo? = null

    for (it in resultados) {
        val idSorteo = it["id_sorteo"].toString().substring(9, 11)
        val fechaSorteo = it["fecha"].toString()
        val precioSorteo = it["precio"].toString()

        if (idSorteo == id) {
            sorteoEncontrado = Sorteo(id = idSorteo, fecha = fechaSorteo, precio = precioSorteo)
            break
        }
    }

    return sorteoEncontrado!!

}

data class Sorteo(
    val id: String,
    val fecha: String,
    val precio: String

)

//suspend fun main() {
//    val result = foo("43")
//    println(result)
//}