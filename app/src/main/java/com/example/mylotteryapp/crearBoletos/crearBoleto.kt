package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.models.Bonoloto
import com.example.mylotteryapp.models.ElGordo
import com.example.mylotteryapp.models.EuroDreams
import com.example.mylotteryapp.models.EuroMillones
import com.example.mylotteryapp.models.Primitiva
import io.realm.kotlin.ext.toRealmList

fun crearBoleto(data: String): Boletos {

    val info = data.split(";")
    val serialNumber = info[0].substringAfter("=").takeLast(10).toLong()
    val fechaString = info[2].substringAfter("=").slice(3..9)
    val fechaRealm = fechaToRealmInstant(fechaString)
    val numeroSorteosJugados = info[2].substringAfter("=").last().digitToInt()
    val partesCombinaciones = info[4].split(".").drop(1)

    var boleto = Boletos()

    when (info[1]) {

        // Primitiva
        "P=1" -> {
            val jokerPrimitiva: String = info[7].substringAfter("=")
            val numReintegro = info[6].substringAfter("=")
            val combinacionesJugadas = mutableListOf<String>()
            combinacionesJugadas.addAll(partesCombinaciones.map {
                it.substringAfter("=").chunked(2).joinToString(" ")
            })
            val precioPrimitiva: Double = if (jokerPrimitiva != "NO") {
                (((combinacionesJugadas.size * 1.0) + 1) * numeroSorteosJugados)
            } else {
                ((combinacionesJugadas.size * 1.0) * numeroSorteosJugados)
            }

            boleto = Boletos().apply {
                primitivas?.add(Primitiva().apply {
                    numeroSerie = serialNumber
                    fecha = fechaRealm
                    combinaciones = combinacionesJugadas.toRealmList()
                    reintegro = numReintegro
                    precio = precioPrimitiva
                    premio = 0.0
                    joker = jokerPrimitiva
                })

            }
        }

        // Bonoloto
        "P=2" -> {
            val numReintegro = info[6].substringAfter("=")
            val combinacionesJugadas = mutableListOf<String>()
            combinacionesJugadas.addAll(partesCombinaciones.map {
                it.substringAfter("=").chunked(2).joinToString(" ")
            })
            val precioBonoloto: Double = ((combinacionesJugadas.size * 0.5) * numeroSorteosJugados)
            boleto = Boletos().apply {
                bonolotos?.add(Bonoloto().apply {
                    numeroSerie = serialNumber
                    fecha = fechaRealm
                    combinaciones = combinacionesJugadas.toRealmList()
                    reintegro = numReintegro
                    precio = precioBonoloto
                    premio = 0.0
                })
            }
        }
        // El Gordo
        "P=4" -> {
            val clave = partesCombinaciones.map {
                it.substringAfter(":").chunked(2).joinToString(" ")
            }
            val combinacionesJugadas = mutableListOf<String>()
            combinacionesJugadas.addAll(partesCombinaciones.map {
                it.substringAfter("=").chunked(2).dropLast(2).joinToString(" ")
            })
            val precioEuromillones: Double =
                ((combinacionesJugadas.size * 1.5) * numeroSorteosJugados)

            boleto = Boletos().apply {
                gordos?.add(ElGordo().apply {
                    numeroSerie = serialNumber
                    fecha = fechaRealm
                    combinaciones = combinacionesJugadas.toRealmList()
                    numeroClave = clave.toRealmList()
                    precio = precioEuromillones
                    premio = 0.0
                })
            }
        }
        // Euro Millones
        "P=7" -> {
            val stars = partesCombinaciones.map {
                it.substringAfter(":").chunked(2).joinToString(" ")
            }
            val combinacionesJugadas = mutableListOf<String>()
            combinacionesJugadas.addAll(partesCombinaciones.map {
                it.substringAfter("=").chunked(2).dropLast(3).joinToString(" ")
            })
            val numeroMillon = info[6].substringAfter(",").dropLast(1)
            val precioEuromillones: Double =
                ((combinacionesJugadas.size * 2.5) * numeroSorteosJugados)

            boleto = Boletos().apply {
                euroMillones?.add(EuroMillones().apply {
                    numeroSerie = serialNumber
                    fecha = fechaRealm
                    combinaciones = combinacionesJugadas.toRealmList()
                    estrellas = stars.toRealmList()
                    elMillon = numeroMillon
                    precio = precioEuromillones
                    premio = 0.0
                })
            }
        }
        // Euro Dreams
        "P=14" -> {
            val dream = partesCombinaciones.map {
                it.substringAfter(":").chunked(2).joinToString(" ")
            }
            val combinacionesJugadas = mutableListOf<String>()
            combinacionesJugadas.addAll(partesCombinaciones.map {
                it.substringAfter("=").chunked(2).dropLast(2).joinToString(" ")
            })
            val precioEuromillones: Double =
                ((combinacionesJugadas.size * 2.5) * numeroSorteosJugados)

            boleto = Boletos().apply {
                euroDreams?.add(EuroDreams().apply {
                    numeroSerie = serialNumber
                    fecha = fechaRealm
                    combinaciones = combinacionesJugadas.toRealmList()
                    dreams = dream.toRealmList()
                    precio = precioEuromillones
                    premio = 0.0
                })
            }

        }

    }
    return boleto
}