package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.LoteriaNacional

fun crearLoteriaQR(data: String): LoteriaNacional {


    val info = data.split(";")
    val serialNumber = info[0].substringAfter("=").takeLast(10).toLong()
    val fechaString = info[2].substringAfter("=").slice(3..9)
    val fechaRealm = fechaToRealmInstant(fechaString)
    val numeroLoteria = info[4].substringAfter("=")
    val serieLoteria = info[6].substringAfter("=")
    val sorteoLoteria = info[2].substringAfter("=").slice(0..2).toInt()

    val precioLoteria = precioLoteriaNacional(fechaString)

     return LoteriaNacional().apply {
        numeroSerie = serialNumber
        fecha = fechaRealm
        numero = numeroLoteria
        serie = serieLoteria
        sorteo = sorteoLoteria
        precio = precioLoteria
        premio = 0.0
        esPremiado = null
    }

}


