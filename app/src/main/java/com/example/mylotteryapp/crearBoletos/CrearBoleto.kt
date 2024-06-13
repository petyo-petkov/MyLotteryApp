package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.data.ResultasdosRepository
import com.example.mylotteryapp.models.Boleto
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmInstant

suspend fun crearBoleto(data: String, resultRepo: ResultasdosRepository): Boleto {

    val info = data.split(";")
    var boleto = Boleto()

    if (data.startsWith("A=")) {
        val numeroSorteosJugados = info[2].substringAfter("=").last().digitToInt()
        val combinacionesJugadas = mutableListOf<String>()
        val partesCombinaciones = info[4].split(".").drop(1)
        val cdcSorteo = info[0].substringAfter("=").slice(0..4)
        val numeroSorteo = info[2].substringAfter("=").slice(0..2)
        var fechaLong = 0L
        var tipoBoleto = ""
        var gameId = ""
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
                val resultados = resultRepo.getIdSorteoYlaFecha(numeroSorteo, "LAPR")
                fechaLong = resultados.fecha
                idSorteoBoleto = resultados.idSorteo
                tipoBoleto = "Primitiva"
                gameId = "LAPR"
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
                val resultados = resultRepo.getIdSorteoYlaFecha(numeroSorteo, "BONO")
                fechaLong = resultados.fecha
                idSorteoBoleto = resultados.idSorteo
                tipoBoleto = "Bonoloto"
                gameId = "BONO"
                combinacionesJugadas.addAll(partesCombinaciones.map {
                    it.substringAfter("=").chunked(2).joinToString(" ")
                })
                precioBoleto = ((combinacionesJugadas.size * 0.5) * numeroSorteosJugados)
                reintegroBoleto = info[6].substringAfter("=")
            }

            "P=7" -> {
                val resultados = resultRepo.getIdSorteoYlaFecha(numeroSorteo, "EMIL")
                fechaLong = resultados.fecha
                idSorteoBoleto = resultados.idSorteo
                tipoBoleto = "Euromillones"
                gameId = "EMIL"
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
                val resultados = resultRepo.getIdSorteoYlaFecha(numeroSorteo, "ELGR")
                fechaLong = resultados.fecha
                idSorteoBoleto = resultados.idSorteo
                tipoBoleto = "El Gordo"
                gameId = "ELGR"
                combinacionesJugadas.addAll(partesCombinaciones.map {
                    it.substringAfter("=").chunked(2).dropLast(2).joinToString(" ")
                })
                numeroClaveElGordo = partesCombinaciones.map {
                    it.substringAfter(":").chunked(2).joinToString(" ")
                }
                precioBoleto = ((combinacionesJugadas.size * 1.5) * numeroSorteosJugados)
            }

            "P=14" -> {
                val resultados = resultRepo.getIdSorteoYlaFecha(numeroSorteo, "EDMS")
                fechaLong = resultados.fecha
                idSorteoBoleto = resultados.idSorteo
                tipoBoleto = "Euro Dreams"
                gameId = "EDMS"
                combinacionesJugadas.addAll(partesCombinaciones.map {
                    it.substringAfter("=").chunked(2).dropLast(2).joinToString(" ")
                })
                suenos = partesCombinaciones.map {
                    it.substringAfter(":").chunked(2).joinToString(" ")
                }
                precioBoleto = ((combinacionesJugadas.size * 2.5) * numeroSorteosJugados)
            }

            "P=10" -> {
                val resultados = resultRepo.getIdSorteoYlaFecha(numeroSorteo, "LNAC")
                fechaLong = resultados.fecha
                idSorteoBoleto = resultados.idSorteo
                tipoBoleto = "Loteria Nacional"
                gameId = "LNAC"
                numeroLoteriaNacional = info[4].substringAfter("=")

            }

            else -> {}
        }

        boleto = Boleto().apply {
            tipo = tipoBoleto
            gameID = gameId
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


