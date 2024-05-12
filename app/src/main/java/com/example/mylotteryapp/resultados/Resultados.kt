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
/**
 * Obtiene los resultados de un juego de lotería entre dos fechas.
 *
 * @param fechaInicio La fecha de inicio en formato "yyyymmdd".
 * @param fechaFin La fecha de fin en formato "yyyymmdd".
 * @return Una lista de resultados del juego especificado.
 */

suspend inline fun <reified T> resultados(
    desde: String,
    hasta: String,
    client: HttpClient = HttpClient(CIO)
): List<T>{
    val gameID = when (T::class) {
        Euromillones::class -> "EMIL"
        Primitiva::class -> "LAPR"
        LoteriaNacional::class -> "LNAC"
        Bonoloto::class -> "BONO"
        EuroDreams::class -> "EDMS"
        ElGordo::class -> "ELGR"
        else -> throw IllegalArgumentException("Unsupported game type: ${T::class}")
    }
    val url =
        "https://www.loteriasyapuestas.es/servicios/buscadorSorteos?game_id=$gameID&celebrados=true&fechaInicioInclusiva=$desde&fechaFinInclusiva=$hasta"

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




suspend fun main() {
    val desde = "20240501"
    val hasta = "20240507"

    val loteria = resultados<LoteriaNacional>(desde, hasta)
    println(loteria[0].fecha_sorteo)
}
