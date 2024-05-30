package com.example.mylotteryapp.data

import android.util.Log
import com.example.mylotteryapp.models.Boleto
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
import kotlinx.serialization.json.JsonObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ResultasdosRepository @Inject constructor(
     val client: HttpClient
) {
    // Obtiene la información de la API en formato JSON
    suspend fun getInfoFromURL(url: String): List<JsonObject> {
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

    // Obtiene los resultados de los sorteos segun el tipo de sorteo y la fecha
    suspend inline fun <reified T> resultados(fecha: String): List<T> {

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

    // Ya veremos....
    suspend fun getTodosLosResultadoPorFecha(boleto: Boleto): String {
        val formatterResultados = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
        val fecha = formatterResultados.format(Date(boleto.fecha.epochSeconds * 1000))

        when (boleto.tipo) {
            "Primitiva" -> return resultados<ResultadosPrimitiva>(
                fecha
            )[0].combinacion

            "Bonoloto" -> return resultados<ResultadosBonoloto>(
                fecha
            )[0].combinacion

            "Euromillones" -> return resultados<ResultadosEuromillones>(
                fecha
            )[0].combinacion

            "El Gordo" -> return resultados<ResultadosElGordo>(fecha)[0].combinacion

            "Euro Dreams" -> return resultados<ResultadosEuroDreams>(
                fecha
            )[0].combinacion

            else -> return "Boleto desconocido"
        }

    }

    // Premio Loteria Nacional segun el numero
    suspend fun getResultadoPorNumeroLoteria(boleto: Boleto): String {

        val numero = boleto.numeroLoteria
        val idSorteo = boleto.idSorteo!!
        val url =
            "https://www.loteriasyapuestas.es/servicios/premioDecimoWebParaVariosSorteos?decimo=$numero&serie=&fraccion=&importeComunEnCentimos=600&idSorteos=$idSorteo"
        val result = getInfoFromURL(url)
        println(result)
        val premio = result[0]["premio"].toString()
        println(premio)

        return premio
    }

    suspend fun comprobarPremioPrimitiva(boleto: Boleto): String {
        val formatterResultados = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
        val fecha = formatterResultados.format(Date(boleto.fecha.epochSeconds * 1000))
        var premio = ""
        val misCombinaciones = boleto.combinaciones
        val miReintegrio = boleto.reintegro
        val resultadoPrimitiva = resultados<ResultadosPrimitiva>(fecha)
        val combinacionGanadora = resultadoPrimitiva[0].combinacion

        // Extraer los números ganadores de la cadena
        val numerosGanadores = combinacionGanadora
            .substringBefore(" C") // Ignora la parte de C y R
            .split(" - ")
            .map { it.toInt() }
            .toSet() // Usamos un set para facilitar la comparación
        val reintegro = combinacionGanadora.substringAfter("R(").substringBefore(")")
        //val complementario = combinacionGanadora.substringAfter("C(").substringBefore(")")

        // Comprobar cada combinación
        for (combinacion in misCombinaciones) {
            val numerosCombinacion = combinacion
                .split(" ")
                .map { it.toInt() }
            val coincidencias = numerosCombinacion.filter { it in numerosGanadores }

            premio = when {
                (coincidencias.size >= 3) -> "Hay premio"
                (reintegro == miReintegrio) -> "Reintegro"
                else -> "No hay premio"
            }
        }
        return premio
    }


}

