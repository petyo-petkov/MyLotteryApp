package com.example.mylotteryapp.resultados

import android.util.Log
import com.example.mylotteryapp.resultados.modelos.bonoloto.Bonoloto
import com.example.mylotteryapp.resultados.modelos.elGordo.ElGordo
import com.example.mylotteryapp.resultados.modelos.euroDreams.EuroDreams
import com.example.mylotteryapp.resultados.modelos.euromillones.Euromillones
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.LoteriaNacional
import com.example.mylotteryapp.resultados.modelos.primitva.Primitiva
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json


//suspend fun main() {
//
//    val fechaInicio = "20240301"
//    val fechaFin = "20240507"
//    val gameID = "ELGR"
//
//    val client = HttpClient(CIO)
//
//    val url =
//        "https://www.loteriasyapuestas.es/servicios/buscadorSorteos?game_id=$gameID&celebrados=true&fechaInicioInclusiva=$fechaInicio&fechaFinInclusiva=$fechaFin"
//
//    val json = Json {
//        coerceInputValues = true
//        ignoreUnknownKeys = true
//    }
//    val response: HttpResponse = client.get(url)
//    val dataString = response.bodyAsText()
//
//    val result: List<ElGordo> = json.decodeFromString(dataString)
//
//    for (i in result){
//        println(i)
//    }
//
//}

