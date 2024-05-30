package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.data.ResultasdosRepository
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.resultados.getInfoByNumSorteo
import io.realm.kotlin.ext.toRealmList

suspend fun crearBoleto(data: String, resultRepo: ResultasdosRepository): Boleto {

    val info = data.split(";")
    var boleto = Boleto()

    if (data.startsWith("A=")) {

        val numeroSerieBoleto = info[0].substringAfter("=").takeLast(10).toLong()
        val numeroSorteosJugados = info[2].substringAfter("=").last().digitToInt()
        val combinacionesJugadas = mutableListOf<String>()
        val partesCombinaciones = info[4].split(".").drop(1)
        val fechaString = info[2].substringAfter('=').slice(3..9)
        val fechaRealm = fechaToRealmInstant(fechaString)

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
        var sorteoLoteriaNacional = ""


        when (info[1]) {
            "P=1" -> {
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

            "P=10" -> {
                tipoBoleto = "Loteria Nacional"
                numeroLoteriaNacional = info[4].substringAfter("=")
                serieLoteriaNacional = info[6].substringAfter("=")
                sorteoLoteriaNacional = info[2].substringAfter("=").slice(0..2)
                //precioBoleto = precioLoteriaNacional(fechaString)
                precioBoleto = getInfoByNumSorteo(sorteoLoteriaNacional, resultRepo).precioSorteo.toDouble()
            }

            else -> {}


        }

        boleto = Boleto().apply {
            tipo = tipoBoleto
            numeroSerie = numeroSerieBoleto
            fecha = fechaRealm
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
            idSorteo = serieLoteriaNacional
            sorteoLoteria = sorteoLoteriaNacional
        }
    } else if (data.length == 20){

        boleto = crearLoteriaFromBarCode(data, resultRepo)

    }

    return boleto
}


