package com.example.mylotteryapp.resultados

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject


suspend fun getInfoFromURL(url: String): List<JsonObject> {
    val client = HttpClient()
    val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }
    return try {
        val response: HttpResponse = client.get(url)
        val dataString = response.bodyAsText()
        json.decodeFromString(dataString)
    } catch (e: Exception) {
        Log.e("error resultados", e.message.toString())
        throw e
    } finally {
        client.close()
    }

}

