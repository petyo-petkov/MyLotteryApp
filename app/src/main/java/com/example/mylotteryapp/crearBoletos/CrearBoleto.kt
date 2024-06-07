package com.example.mylotteryapp.crearBoletos

import android.util.Log
import com.example.mylotteryapp.data.ResultasdosRepository
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.resultados.modelos.euromillones.ResultadosEuromillones
import com.example.mylotteryapp.resultados.modelos.euromillones.proximos.ResultadoProximosEMIL
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.ResultadosLoteriaNacional
import com.example.mylotteryapp.resultados.modelos.primitva.ResultadosPrimitiva
import com.example.mylotteryapp.resultados.modelos.primitva.proximos.ResultadoProximosLAPR
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_EMIL
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_LAPR
import com.example.mylotteryapp.resultados.urls.GET_ULTIMOS_SORTEOS_EMIL
import com.example.mylotteryapp.resultados.urls.GET_ULTIMOS_SORTEOS_LAPR
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmInstant
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

suspend fun crearBoleto(data: String, resultRepo: ResultasdosRepository): Boleto {

    val info = data.split(";")
    var boleto = Boleto()

    if (data.startsWith("A=")) {
        val numeroSorteosJugados = info[2].substringAfter("=").last().digitToInt()
        val combinacionesJugadas = mutableListOf<String>()
        val partesCombinaciones = info[4].split(".").drop(1)
        val cdcSorteo = info[0].substringAfter("=").slice(0..4)
//        val fechaString = info[2].substringAfter('=').slice(3..9)
//        val fechaRealm = fechaToRealmInstant(fechaString)

//        var fechaString = ""
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
//        val fechaLong = formatter.parse(fechaString)!!.time
        var fechaLong = 0L

        val fechaDeEntradaFormatter = DateTimeFormatter.ofPattern("ddMMMyy")
        val fechaDeSalidaFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

        var tipoBoleto = ""
        var precioBoleto = 0.0
        val premioBoleto = 0.0
        var jokerPrimitiva = ""
        var reintegroBoleto = ""
        var numeroClaveElGordo: List<String> = emptyList()
        var suenos: List<String> = emptyList()
        var estrellasEuromillones: List<String> = emptyList()
        var numeroMillones = ""
        var numeroLoteriaNacional = ""
        var serieLoteriaNacional = ""
        var idSorteoBoleto = ""


        when (info[1]) {
            "P=1" -> {
                val infoProximosLAPR = resultRepo.getInfoFromURL<ResultadoProximosLAPR>(GET_PROXIMOS_SORTEOS_LAPR)
                val infoUltimosLAPR = resultRepo.getInfoFromURL<ResultadosPrimitiva>(GET_ULTIMOS_SORTEOS_LAPR)
                val cdcProximo = infoProximosLAPR.find { it.cdc == cdcSorteo }
                val cdcUltimo = infoUltimosLAPR.find { it.cdc == cdcSorteo }
                cdcProximo?.let {
                    fechaLong = formatter.parse(it.fecha)!!.time
                    idSorteoBoleto = it.id_sorteo
                }
                cdcUltimo?.let {
                    fechaLong = formatter.parse(it.fecha_sorteo)!!.time
                    idSorteoBoleto = it.id_sorteo
                }
                tipoBoleto = "Primitiva"
                jokerPrimitiva = info[7].substringAfter("=")
                reintegroBoleto = info[6].substringAfter("=")
                combinacionesJugadas.addAll(partesCombinaciones.map {
                    it.substringAfter("=").chunked(2).joinToString(" ")
                })
                precioBoleto = if (jokerPrimitiva != "NO") {
                    (((combinacionesJugadas.size * 1.0) + 1) * numeroSorteosJugados)
                } else {
                    ((combinacionesJugadas.size * 1.0) * numeroSorteosJugados)
                }

            }

            "P=2" -> {
                tipoBoleto = "Bonoloto"
                combinacionesJugadas.addAll(partesCombinaciones.map {
                    it.substringAfter("=").chunked(2).joinToString(" ")
                })
                precioBoleto = ((combinacionesJugadas.size * 0.5) * numeroSorteosJugados)
                reintegroBoleto = info[6].substringAfter("=")
            }

            "P=7" -> {
                val infoProximosEMIL = resultRepo.getInfoFromURL<ResultadoProximosEMIL>(GET_PROXIMOS_SORTEOS_EMIL)
                val infoUltimosEMIL = resultRepo.getInfoFromURL<ResultadosEuromillones>(GET_ULTIMOS_SORTEOS_EMIL)
                val cdcProximo = infoProximosEMIL.find { it.cdc == cdcSorteo }
                val cdcUltimo = infoUltimosEMIL.find { it.cdc == cdcSorteo }
                cdcProximo?.let {
                    fechaLong = formatter.parse(it.fecha)!!.time
                    idSorteoBoleto = it.id_sorteo
                }
                cdcUltimo?.let {
                    fechaLong = formatter.parse(it.fecha_sorteo)!!.time
                    idSorteoBoleto = it.id_sorteo
                }

                tipoBoleto = "Euromillones"
                combinacionesJugadas.addAll(partesCombinaciones.map {
                    it.substringAfter("=").chunked(2).dropLast(3).joinToString(" ")
                })
                estrellasEuromillones = partesCombinaciones.map {
                    it.substringAfter(":").chunked(2).joinToString(" ")
                }
                numeroMillones = info[6].substringAfter(",").dropLast(1)
                precioBoleto = ((combinacionesJugadas.size * 2.5) * numeroSorteosJugados)

            }

            "P=4" -> {
                tipoBoleto = "El Gordo"
                combinacionesJugadas.addAll(partesCombinaciones.map {
                    it.substringAfter("=").chunked(2).dropLast(2).joinToString(" ")
                })
                numeroClaveElGordo = partesCombinaciones.map {
                    it.substringAfter(":").chunked(2).joinToString(" ")
                }
                precioBoleto = ((combinacionesJugadas.size * 1.5) * numeroSorteosJugados)
            }

            "P=14" -> {
                tipoBoleto = "Euro Dreams"
                combinacionesJugadas.addAll(partesCombinaciones.map {
                    it.substringAfter("=").chunked(2).dropLast(2).joinToString(" ")
                })
                suenos = partesCombinaciones.map {
                    it.substringAfter(":").chunked(2).joinToString(" ")
                }
                precioBoleto = ((combinacionesJugadas.size * 2.5) * numeroSorteosJugados)
            }

//            "P=10" -> {
//                tipoBoleto = "Loteria Nacional"
//                numeroLoteriaNacional = info[4].substringAfter("=")
//                serieLoteriaNacional = info[6].substringAfter("=")
//                precioBoleto = resultRepo.resultados<ResultadosLoteriaNacional>(
//                    (LocalDate.parse(fechaString, fechaDeEntradaFormatter)).format(fechaDeSalidaFormatter)
//                )[0].precioDecimo
//            }

            else -> {}
        }

        boleto = Boleto().apply {
            tipo = tipoBoleto
            numeroSerie = info[0].substringAfter("=").takeLast(10).toLong()
            fecha = RealmInstant.from(fechaLong / 1000, 0)
            precio = precioBoleto
            premio = premioBoleto
            combinaciones = combinacionesJugadas.toRealmList()
            reintegro = reintegroBoleto
            joker = jokerPrimitiva
            numeroClave = numeroClaveElGordo.toRealmList()
            dreams = suenos.toRealmList()
            estrellas = estrellasEuromillones.toRealmList()
            numeroElMillon = numeroMillones
            numeroLoteria = numeroLoteriaNacional
            cdc = cdcSorteo
            idSorteo = idSorteoBoleto
            numSorteo = info[2].substringAfter("=").slice(0..2).toInt()

        }
    } else if (data.length == 20){

        boleto = crearLoteriaFromBarCode(data, resultRepo)

    }

    return boleto
}


