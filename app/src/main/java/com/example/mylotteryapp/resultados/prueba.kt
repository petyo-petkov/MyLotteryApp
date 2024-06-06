package com.example.mylotteryapp.resultados

import android.util.Log
import com.example.mylotteryapp.resultados.modelos.primitva.proximos.ResultadoProximosLAPR
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_LAPR
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json


suspend inline fun <reified T> getInfoFromURL(url: String): List<T> {
    val client = HttpClient()
    val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true

    }
    try {
        val response: HttpResponse = client.get(url)
        val dataString = response.bodyAsText()
        return json.decodeFromString(dataString)
    } catch (e: Exception) {
        Log.e("ERROR en obtener resultados deasde $url", e.message.toString())
        throw e
    }

}




data class proximoSorteo(
    val fecha: String,
    val cdc: String,
    val game_id: String,
)