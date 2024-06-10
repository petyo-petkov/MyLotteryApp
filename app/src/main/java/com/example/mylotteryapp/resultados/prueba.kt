package com.example.mylotteryapp.resultados

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_TODOS
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale


suspend  fun  getInfoFromURL(url: String): List<JsonObject> {
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
