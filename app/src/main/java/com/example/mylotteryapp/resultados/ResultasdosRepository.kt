package com.example.mylotteryapp.resultados

import android.util.Log
import com.example.mylotteryapp.resultados.modelos.bonoloto.ResultadosBonoloto
import com.example.mylotteryapp.resultados.modelos.elGordo.ResultadosElGordo
import com.example.mylotteryapp.resultados.modelos.euroDreams.ResultadosEuroDreams
import com.example.mylotteryapp.resultados.modelos.euromillones.ResultadosEuromillones
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.ResultadosLoteriaNacional
import com.example.mylotteryapp.resultados.modelos.primitva.ResultadosPrimitiva
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Obtiene los resultados de un juego de lotería entre dos fechas.
 *
 * @param fecha La fecha de inicio en formato "yyyymmdd".
 * @return Una lista de resultados del juego especificado.
 */

class ResultasdosRepository @Inject constructor(
     val client: HttpClient
) {
    suspend inline fun <reified T> resultados(
        fecha: String,

        ): List<T> {
        val gameID = when (T::class) {
            ResultadosEuromillones::class -> "EMIL"
            ResultadosPrimitiva::class -> "LAPR"
            ResultadosLoteriaNacional::class -> "LNAC"
            ResultadosBonoloto::class -> "BONO"
            ResultadosEuroDreams::class -> "EDMS"
            ResultadosElGordo::class -> "ELGR"
            else -> throw IllegalArgumentException("Unsupported game type: ${T::class}")
        }
        val url =
            "https://www.loteriasyapuestas.es/servicios/buscadorSorteos?game_id=$gameID&celebrados=true&fechaInicioInclusiva=$fecha&fechaFinInclusiva=$fecha"

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
        }

    }
}
