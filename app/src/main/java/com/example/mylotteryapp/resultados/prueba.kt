package com.example.mylotteryapp.resultados

import android.util.Log
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.ResultadosLoteriaNacional
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.ResultadosProximosSorteos
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_LNAC
import com.example.mylotteryapp.resultados.urls.GET_ULTIMOS_SORTEOS_LNAC
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

//suspend inline fun <reified T : Any> getInfoFromURLPrueba(url: String): List<T> {
//    val client = HttpClient(CIO)
//    return try {
//        val response: HttpResponse = client.get(url)
//        val dataString = response.bodyAsText()
//        Json.decodeFromString(dataString)
//    } catch (e: Exception) {
//        Log.e("error resultados", e.message.toString())
//        throw e
//    }
//}
//
//suspend fun main1(numSorteo: Int): InfoSorteoPrueba {
//
//    val urlProximosSorteos = GET_PROXIMOS_SORTEOS_LNAC
//    val urlUltimosSorteos = GET_ULTIMOS_SORTEOS_LNAC
//    val proximosSorteos = getInfoFromURLPrueba<ResultadosProximosSorteos>(urlProximosSorteos)
//    val ultimosSorteos = getInfoFromURLPrueba<ResultadosLoteriaNacional>(urlUltimosSorteos)
//
//    val resultProximo = proximosSorteos.find { sorteo ->
//        sorteo.id_sorteo.substring(7, 10).toInt() == numSorteo
//    }?.let { resultProximo ->
//        InfoSorteoPrueba(
//            idSorteo = resultProximo.id_sorteo,
//            fechaSorteo = resultProximo.fecha,
//            precioSorteo = resultProximo.precio.toDouble(),
//            gameID = resultProximo.game_id,
//            diaSemana = resultProximo.dia_semana,
//            numSorteo = numSorteo,
//            apertura = resultProximo.apertura,
//            cierre = resultProximo.cierre
//        )
//    }
//    val resultUltimo = ultimosSorteos.find { sorteo ->
//        sorteo.num_sorteo.toInt() == numSorteo
//    }?.let { resultUltimo ->
//        InfoSorteoPrueba(
//            idSorteo = resultUltimo.id_sorteo,
//            fechaSorteo = resultUltimo.fecha_sorteo,
//            precioSorteo = resultUltimo.precioDecimo,
//            gameID = resultUltimo.game_id,
//            diaSemana = resultUltimo.dia_semana,
//            numSorteo = numSorteo,
//            apertura = resultUltimo.apertura,
//            cierre = resultUltimo.cierre
//        )
//    }
//    return when {
//        resultUltimo != null -> resultUltimo
//        resultProximo != null -> resultProximo
//        else -> InfoSorteoPrueba()
//    }
//
//}

//data class InfoSorteoPrueba(
//    val numSorteo: Int = 0,
//    val idSorteo: String = "",
//    val fechaSorteo: String = "",
//    val precioSorteo: Double = 0.0,
//    val gameID: String = "",
//    val diaSemana: String = "",
//    val apertura: String = "",
//    val cierre: String = ""
//)




