package com.example.mylotteryapp.getInfoLoterias

import android.util.Log
import com.example.mylotteryapp.getInfoLoterias.modelos.bonoloto.Bonoloto
import com.example.mylotteryapp.getInfoLoterias.modelos.euromillones.Euromillones
import com.example.mylotteryapp.getInfoLoterias.modelos.loteriaNacional.LoteriaNacional
import com.example.mylotteryapp.getInfoLoterias.modelos.primitva.Primitiva
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json


suspend inline fun <reified T> getResultados(fechaInicio: String, fechaFin: String): List<T>{

    val client = HttpClient(CIO)

    val gameID = when (T::class) {
        Euromillones::class -> "EMIL"
        Primitiva::class -> "LAPR"
        LoteriaNacional::class -> "LNAC"
        Bonoloto::class -> "BONO"

        else -> throw IllegalArgumentException("Unsupported game type: ${T::class}")
    }
    val url =
        "https://www.loteriasyapuestas.es/servicios/buscadorSorteos?game_id=$gameID&celebrados=true&fechaInicioInclusiva=$fechaInicio&fechaFinInclusiva=$fechaFin"

    val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    try{
        val response: HttpResponse = client.get(url)
        val dataString = response.bodyAsText()
        val result: List<T> = json.decodeFromString(dataString)
        return result
    } catch (e: Exception) {
        Log.e("error resultados", e.message.toString())
        throw e
    } finally {
        client.close()
    }

}


suspend fun main() {

    val fechaInicio = "20240501"
    val fechaFin = "20240507"

    val loteria = getResultados<Bonoloto>(fechaInicio, fechaFin)
    println(loteria[0].fecha_sorteo)
}
