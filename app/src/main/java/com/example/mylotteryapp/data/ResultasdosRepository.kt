package com.example.mylotteryapp.data

import android.util.Log
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.resultados.modelos.bonoloto.ResultadosBonoloto
import com.example.mylotteryapp.resultados.modelos.elGordo.ResultadosElGordo
import com.example.mylotteryapp.resultados.modelos.euroDreams.ResultadosEuroDreams
import com.example.mylotteryapp.resultados.modelos.euromillones.ResultadosEuromillones
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.ResultadosLoteriaNacional
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.proximos.ResultadoProximosLNAC
import com.example.mylotteryapp.resultados.modelos.primitva.ResultadosPrimitiva
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_LNAC
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_TODOS
import com.example.mylotteryapp.resultados.urls.GET_ULTIMOS_SORTEOS_LNAC
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ResultasdosRepository @Inject constructor(
    val client: HttpClient,
) {

    suspend inline fun <reified T> getInfoFromURL(url: String): List<T> {
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

    suspend fun findSorteo(url: String, gameID: String, numSorteo: String): JsonObject? {
        return getInfoFromURL<JsonObject>(url)
            .find {
                it.get("id_sorteo").toString().substring(8..10) == numSorteo && it.get("game_id")
                    .toString().slice(1..4) == gameID
            }
    }

    suspend fun getIdSorteoYlaFecha(numSorteo: String, gameID: String): InfoSorteo {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formatterToLong = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val hoy = LocalDate.now()
        val hoyMenusTres = hoy.minusMonths(3)
        val fechaInicio = hoyMenusTres.format(formatter)
        val fechaFin = hoy.format(formatter)
        val urlUltimos =
            "https://www.loteriasyapuestas.es/servicios/buscadorSorteos?game_id=$gameID&celebrados=true&fechaInicioInclusiva=$fechaInicio&fechaFinInclusiva=$fechaFin"

        val proximos = findSorteo(GET_PROXIMOS_SORTEOS_TODOS, gameID, numSorteo)
        val ultimos = findSorteo(urlUltimos, gameID, numSorteo)
        val fecha = when {
            ultimos != null -> ultimos.get("fecha_sorteo").toString().substring(1..19)
            proximos != null -> proximos.get("fecha").toString().substring(1..19)
            else -> "1980-12-04 16:45:00"
        }
        val fechaLong = formatterToLong.parse(fecha)!!.time

        return InfoSorteo(
            fecha = fechaLong,
            idSorteo = when {
                ultimos != null -> ultimos.get("id_sorteo").toString().slice(1..10)
                proximos != null -> proximos.get("id_sorteo").toString().slice(1..10)
                else -> "000"
            }
        )

    }


    suspend inline fun <reified T> getInfoPorFechas(
        fechaInicio: String,
        fechaFin: String
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
        // la fecha tiene que ser en el formato "20240528" ("yyyyMMdd")
        val url =
            "https://www.loteriasyapuestas.es/servicios/buscadorSorteos?game_id=$gameID&celebrados=true&fechaInicioInclusiva=$fechaInicio&fechaFinInclusiva=$fechaFin"

        return getInfoFromURL<T>(url)

    }

    suspend fun getTodosLosResultadoPorFecha(boleto: Boleto): String {
        val formatterResultados = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
        val fecha = formatterResultados.format(Date(boleto.fecha.epochSeconds * 1000))

        when (boleto.tipo) {
            "Primitiva" -> return getInfoPorFechas<ResultadosPrimitiva>(
                fecha, fecha
            )[0].combinacion

            "Bonoloto" -> return getInfoPorFechas<ResultadosBonoloto>(
                fecha, fecha
            )[0].combinacion

            "Euromillones" -> return getInfoPorFechas<ResultadosEuromillones>(
                fecha, fecha
            )[0].combinacion

            "El Gordo" -> return getInfoPorFechas<ResultadosElGordo>(fecha, fecha)[0].combinacion

            "Euro Dreams" -> return getInfoPorFechas<ResultadosEuroDreams>(
                fecha, fecha
            )[0].combinacion

            "Loteria Nacional" -> return getInfoPorFechas<ResultadosLoteriaNacional>(
                fecha, fecha
            )[0].primerPremio.decimo

            else -> return "Boleto desconocido"
        }

    }

    suspend fun comprobarPremioLAPR(boleto: Boleto): String {
        val formatterResultados = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
        val fecha = formatterResultados.format(Date(boleto.fecha.epochSeconds * 1000))
        var premio = ""
        val misCombinaciones = boleto.combinaciones
        val miReintegrio = boleto.reintegro
        val resultadoPrimitiva = getInfoPorFechas<ResultadosPrimitiva>(fecha, fecha)
        val combinacionGanadora = resultadoPrimitiva[0].combinacion
        val numerosGanadores = combinacionGanadora
            .substringBefore(" C")
            .split(" - ")
            .map { it.toInt() }
            .toSet()
        val reintegro = combinacionGanadora.substringAfter("R(").substringBefore(")")
        val complementario = combinacionGanadora.substringAfter("C(").substringBefore(")").toInt()

        for (combinacion in misCombinaciones) {
            val numerosCombinacion = combinacion
                .split(" ")
                .map { it.toInt() }
            val coincidencias = numerosCombinacion.filter { it in numerosGanadores }
            premio = when {
                (coincidencias.size == 3) -> resultadoPrimitiva[0].escrutinio[5].premio
                (coincidencias.size == 4) -> resultadoPrimitiva[0].escrutinio[4].premio
                (coincidencias.size == 5) -> resultadoPrimitiva[0].escrutinio[3].premio
                (coincidencias.size >= 5 && numerosCombinacion.contains(complementario)) -> resultadoPrimitiva[0].escrutinio[2].premio
                (coincidencias.size == 6) -> resultadoPrimitiva[0].escrutinio[1].premio
                (reintegro == miReintegrio) -> "Reintegro"
                else -> "No hay premio"
            }
        }
        return premio
    }

    suspend fun comprobarPremioEDMS(boleto: Boleto): String {
        val formatterResultados = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
        val fecha = formatterResultados.format(Date(boleto.fecha.epochSeconds * 1000))
        var premio = ""
        val misCombinaciones = boleto.combinaciones
        val miDreams = boleto.dreams
        val resultadosEDMS = getInfoPorFechas<ResultadosEuroDreams>(fecha, fecha)
        val combinacionGanadora = resultadosEDMS[0].combinacion
        val numerosGanadores = combinacionGanadora
            .substringBefore(" C")
            .split(" - ")
            .map { it.toInt() }
            .toSet()
        val dreamGanador = combinacionGanadora.substringAfter("C(").substringBefore(")").toInt()
        for (combinacion in misCombinaciones) {
            for (dream in miDreams) {
                val numerosCombinacion = combinacion
                    .split(" ")
                    .map { it.toInt() }
                val coincidencias = numerosCombinacion.filter { it in numerosGanadores }
                val isDream = dream.toInt() == dreamGanador
                premio = when {
                    (coincidencias.size == 2 && isDream) -> resultadosEDMS[0].escrutinio[5].premio
                    (coincidencias.size == 3 && isDream) -> resultadosEDMS[0].escrutinio[4].premio
                    (coincidencias.size == 4 && isDream) -> resultadosEDMS[0].escrutinio[3].premio
                    (coincidencias.size == 5 && isDream) -> resultadosEDMS[0].escrutinio[2].premio
                    (coincidencias.size == 6 ) -> resultadosEDMS[0].escrutinio[1].premio
                    (coincidencias.size >= 6 && isDream) -> resultadosEDMS[0].escrutinio[0].premio
                    else -> {
                        "No hay premio"
                    }
                }

            }
        }
        return premio
    }


    suspend fun getPremioLoteriaNacional(boleto: Boleto): Double {
        val url =
            "https://www.loteriasyapuestas.es/servicios/premioDecimoWebParaVariosSorteos?decimo=${boleto.numeroLoteria}&serie=&fraccion=&importeComunEnCentimos&idSorteos=${boleto.idSorteo}"
        return getInfoFromURL<JsonObject>(url)[0]["premioEnCentimos"].toString().toDouble() / 100
    }

    suspend fun getInfoLNACbyNumSorteo(numSorteo: Int): InfoSorteoLNAC {
        val proximosSorteos = getInfoFromURL<ResultadoProximosLNAC>(GET_PROXIMOS_SORTEOS_LNAC)
        val ultimosSorteos = getInfoFromURL<ResultadosLoteriaNacional>(GET_ULTIMOS_SORTEOS_LNAC)

        val resultProximo = proximosSorteos.find { sorteo ->
            sorteo.id_sorteo.substring(7, 10).toInt() == numSorteo
        }?.let { resultProximo ->
            InfoSorteoLNAC(
                idSorteo = resultProximo.id_sorteo,
                fechaSorteo = resultProximo.fecha,
                precioSorteo = resultProximo.precio.toDouble(),
                gameID = resultProximo.game_id,
                diaSemana = resultProximo.dia_semana,
                numSorteo = numSorteo,
                apertura = resultProximo.apertura,
                cierre = resultProximo.cierre
            )
        }
        val resultUltimo = ultimosSorteos.find { sorteo ->
            sorteo.num_sorteo.toInt() == numSorteo
        }?.let { result ->
            InfoSorteoLNAC(
                idSorteo = result.id_sorteo,
                fechaSorteo = result.fecha_sorteo,
                precioSorteo = result.precioDecimo,
                gameID = result.game_id,
                diaSemana = result.dia_semana,
                numSorteo = numSorteo,
                apertura = result.apertura,
                cierre = result.cierre
            )
        }
        return when {
            resultUltimo != null -> resultUltimo
            resultProximo != null -> resultProximo
            else -> InfoSorteoLNAC()
        }

    }


}

data class InfoSorteoLNAC(
    val numSorteo: Int = 0,
    val idSorteo: String = "",
    val fechaSorteo: String = "",
    val precioSorteo: Double = 0.0,
    val gameID: String = "",
    val diaSemana: String = "",
    val apertura: String = "",
    val cierre: String = ""
)

@Serializable
data class InfoSorteo(
    val idSorteo: String,
    val fecha: Long,
)