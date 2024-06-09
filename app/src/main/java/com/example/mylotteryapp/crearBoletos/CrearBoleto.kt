package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.data.ResultasdosRepository
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.resultados.modelos.bonoloto.ResultadosBonoloto
import com.example.mylotteryapp.resultados.modelos.bonoloto.proximos.ResultadoProximosBONO
import com.example.mylotteryapp.resultados.modelos.elGordo.ResultadosElGordo
import com.example.mylotteryapp.resultados.modelos.elGordo.proximos.ResultadoProximosELGR
import com.example.mylotteryapp.resultados.modelos.euroDreams.ResultadosEuroDreams
import com.example.mylotteryapp.resultados.modelos.euroDreams.proximos.ResultadoProximosEDMS
import com.example.mylotteryapp.resultados.modelos.euromillones.ResultadosEuromillones
import com.example.mylotteryapp.resultados.modelos.euromillones.proximos.ResultadoProximosEMIL
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.ResultadosLoteriaNacional
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.proximos.ResultadoProximosLNAC
import com.example.mylotteryapp.resultados.modelos.primitva.ResultadosPrimitiva
import com.example.mylotteryapp.resultados.modelos.primitva.proximos.ResultadoProximosLAPR
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_BONO
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_EDMS
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_ELGR
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_EMIL
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_LAPR
import com.example.mylotteryapp.resultados.urls.GET_PROXIMOS_SORTEOS_LNAC
import com.example.mylotteryapp.resultados.urls.GET_ULTIMOS_SORTEOS_BONO
import com.example.mylotteryapp.resultados.urls.GET_ULTIMOS_SORTEOS_EDMS
import com.example.mylotteryapp.resultados.urls.GET_ULTIMOS_SORTEOS_ELGR
import com.example.mylotteryapp.resultados.urls.GET_ULTIMOS_SORTEOS_EMIL
import com.example.mylotteryapp.resultados.urls.GET_ULTIMOS_SORTEOS_LAPR
import com.example.mylotteryapp.resultados.urls.GET_ULTIMOS_SORTEOS_LNAC
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmInstant
import java.text.SimpleDateFormat
import java.util.Locale

suspend fun crearBoleto(data: String, resultRepo: ResultasdosRepository): Boleto {

    val info = data.split(";")
    var boleto = Boleto()

    if (data.startsWith("A=")) {
        val numeroSorteosJugados = info[2].substringAfter("=").last().digitToInt()
        val combinacionesJugadas = mutableListOf<String>()
        val partesCombinaciones = info[4].split(".").drop(1)
        val cdcSorteo = info[0].substringAfter("=").slice(0..4)
        val numeroSorteo = info[2].substringAfter("=").slice(0..2)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        var fechaLong = 0L
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
        var idSorteoBoleto = ""


        when (info[1]) {
            "P=1" -> {
                val proximo =
                    resultRepo.getInfoFromURL<ResultadoProximosLAPR>(GET_PROXIMOS_SORTEOS_LAPR)
                        .find {
                            it.id_sorteo.substring(7..9) == numeroSorteo
                        }
                val ultimo =
                    resultRepo.getInfoFromURL<ResultadosPrimitiva>(GET_ULTIMOS_SORTEOS_LAPR).find {
                        it.id_sorteo.substring(7..9) == numeroSorteo
                    }

                fechaLong = when {
                    proximo != null -> formatter.parse(proximo.fecha)!!.time
                    ultimo != null -> formatter.parse(ultimo.fecha_sorteo)!!.time
                    else -> fechaToLong(info[2].substringAfter('=').slice(3..9))
                }
                idSorteoBoleto = when {
                    proximo != null -> proximo.id_sorteo
                    ultimo != null -> ultimo.id_sorteo
                    else -> "0000001"
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
                val proximo =
                    resultRepo.getInfoFromURL<ResultadoProximosBONO>(GET_PROXIMOS_SORTEOS_BONO)
                        .find {
                            it.id_sorteo.substring(7..9) == numeroSorteo
                        }
                val ultimo =
                    resultRepo.getInfoFromURL<ResultadosBonoloto>(GET_ULTIMOS_SORTEOS_BONO).find {
                        it.id_sorteo.substring(7..9) == numeroSorteo
                    }

                fechaLong = when {
                    proximo != null -> formatter.parse(proximo.fecha)!!.time
                    ultimo != null -> formatter.parse(ultimo.fecha_sorteo)!!.time
                    else -> fechaToLong(info[2].substringAfter('=').slice(3..9))
                }
                idSorteoBoleto = when {
                    proximo != null -> proximo.id_sorteo
                    ultimo != null -> ultimo.id_sorteo
                    else -> "0000001"
                }
                tipoBoleto = "Bonoloto"
                combinacionesJugadas.addAll(partesCombinaciones.map {
                    it.substringAfter("=").chunked(2).joinToString(" ")
                })
                precioBoleto = ((combinacionesJugadas.size * 0.5) * numeroSorteosJugados)
                reintegroBoleto = info[6].substringAfter("=")
            }

            "P=7" -> {
                val proximo =
                    resultRepo.getInfoFromURL<ResultadoProximosEMIL>(GET_PROXIMOS_SORTEOS_EMIL)
                        .find {
                            it.id_sorteo.substring(7..9) == numeroSorteo
                        }
                val ultimo =
                    resultRepo.getInfoFromURL<ResultadosEuromillones>(GET_ULTIMOS_SORTEOS_EMIL)
                        .find {
                            it.id_sorteo.substring(7..9) == numeroSorteo
                        }
                fechaLong = when {
                    proximo != null -> formatter.parse(proximo.fecha)!!.time
                    ultimo != null -> formatter.parse(ultimo.fecha_sorteo)!!.time
                    else -> fechaToLong(info[2].substringAfter('=').slice(3..9))
                }
                idSorteoBoleto = when {
                    proximo != null -> proximo.id_sorteo
                    ultimo != null -> ultimo.id_sorteo
                    else -> "0000001"
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
                val proximo =
                    resultRepo.getInfoFromURL<ResultadoProximosELGR>(GET_PROXIMOS_SORTEOS_ELGR)
                        .find {
                            it.id_sorteo.substring(7..9) == numeroSorteo
                        }
                val ultimo =
                    resultRepo.getInfoFromURL<ResultadosElGordo>(GET_ULTIMOS_SORTEOS_ELGR).find {
                        it.id_sorteo.substring(7..9) == numeroSorteo
                    }

                fechaLong = when {
                    proximo != null -> formatter.parse(proximo.fecha)!!.time
                    ultimo != null -> formatter.parse(ultimo.fecha_sorteo)!!.time
                    else -> fechaToLong(info[2].substringAfter('=').slice(3..9))
                }
                idSorteoBoleto = when {
                    proximo != null -> proximo.id_sorteo
                    ultimo != null -> ultimo.id_sorteo
                    else -> "0000001"
                }
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
                val proximo =
                    resultRepo.getInfoFromURL<ResultadoProximosEDMS>(GET_PROXIMOS_SORTEOS_EDMS)
                        .find {
                            it.id_sorteo.substring(7..9) == numeroSorteo
                        }
                val ultimo =
                    resultRepo.getInfoFromURL<ResultadosEuroDreams>(GET_ULTIMOS_SORTEOS_EDMS).find {
                        it.id_sorteo.substring(7..9) == numeroSorteo
                    }
                fechaLong = when {
                    proximo != null -> formatter.parse(proximo.fecha)!!.time
                    ultimo != null -> formatter.parse(ultimo.fecha_sorteo)!!.time
                    else -> fechaToLong(info[2].substringAfter('=').slice(3..9))
                }
                idSorteoBoleto = when {
                    proximo != null -> proximo.id_sorteo
                    ultimo != null -> ultimo.id_sorteo
                    else -> "0000001"
                }
                tipoBoleto = "Euro Dreams"
                combinacionesJugadas.addAll(partesCombinaciones.map {
                    it.substringAfter("=").chunked(2).dropLast(2).joinToString(" ")
                })
                suenos = partesCombinaciones.map {
                    it.substringAfter(":").chunked(2).joinToString(" ")
                }
                precioBoleto = ((combinacionesJugadas.size * 2.5) * numeroSorteosJugados)
            }

            "P=10" -> {
                val proximo =
                    resultRepo.getInfoFromURL<ResultadoProximosLNAC>(GET_PROXIMOS_SORTEOS_LNAC)
                        .find {
                            it.id_sorteo.substring(7..9) == numeroSorteo
                        }
                val ultimo =
                    resultRepo.getInfoFromURL<ResultadosLoteriaNacional>(GET_ULTIMOS_SORTEOS_LNAC)
                        .find {
                            it.id_sorteo.substring(7..9) == numeroSorteo
                        }
                fechaLong = when {
                    proximo != null -> formatter.parse(proximo.fecha)!!.time
                    ultimo != null -> formatter.parse(ultimo.fecha_sorteo)!!.time
                    else -> fechaToLong(info[2].substringAfter('=').slice(3..9))
                }
                idSorteoBoleto = when {
                    proximo != null -> proximo.id_sorteo
                    ultimo != null -> ultimo.id_sorteo
                    else -> "0000001"
                }
                precioBoleto = when {
                    proximo != null -> proximo.precio.toDouble()
                    ultimo != null -> ultimo.precioDecimo
                    else -> 0.0
                }
                tipoBoleto = "Loteria Nacional"
                numeroLoteriaNacional = info[4].substringAfter("=")

            }

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

    } else if (data.length == 20) {

        boleto = crearLoteriaFromBarCode(data, resultRepo)

    }

    return boleto
}


