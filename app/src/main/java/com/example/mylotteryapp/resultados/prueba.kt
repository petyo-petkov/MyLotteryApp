package com.example.mylotteryapp.resultados

import android.util.Log
import com.example.mylotteryapp.resultados.modelos.primitva.ResultadosPrimitiva
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.text.SimpleDateFormat
import java.util.Locale


suspend fun getInfoFromURL(url: String): List<JsonObject> {
    val client = HttpClient()
    val json = Json {
        isLenient = true
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

suspend fun getInfo(): List<JsonObject> {
    val url = "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=LAPR&num=2"
    val resultados = getInfoFromURL(url)
    return resultados

}

suspend fun main() {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val resultados = getInfo()
    val fecha = resultados[0].get("fecha")?.jsonPrimitive?.content
    println(fecha!!)
    println(formatter.parse(fecha))
}
