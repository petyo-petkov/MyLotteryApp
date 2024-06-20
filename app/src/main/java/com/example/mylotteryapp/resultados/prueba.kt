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



 fun main() {
     val combinacionGanadora = " 03 - 11 - 33 - 34 - 36 - 01 - 12"
     val numerosGanadores = combinacionGanadora
         .substringAfter(" ")
         .split(" - ")
         .slice(0..4)
         .map { it.toInt() }
         .toSet()
     val estrellasGanadora = combinacionGanadora
         .split(" - ")
         .slice(5..6)
         .map { it.toInt() }
         .toSet()

     println(numerosGanadores)
     println(estrellasGanadora)
}
