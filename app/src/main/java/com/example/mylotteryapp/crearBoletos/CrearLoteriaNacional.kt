package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.LoteriaNacional

fun crearLoteriaNacional(data: String): LoteriaNacional {

    var loteria = LoteriaNacional()

    if (data.length > 20) {

        val info = data.split(";")
        val serialNumber = info[0].substringAfter("=").takeLast(10).toLong()
        val fechaString = info[2].substringAfter("=").slice(3..9)
        val fechaRealm = fechaToRealmInstant(fechaString)
        val numeroLoteria = info[4].substringAfter("=")
        val serieLoteria = info[6].substringAfter("=")
        val sorteoLoteria = info[2].substringAfter("=").slice(0..2).toInt()

        val precioLoteria = precioLoteriaNacional(fechaString)

        loteria = LoteriaNacional().apply {
            numeroSerie = serialNumber
            fecha = fechaRealm
            numero = numeroLoteria
            serie = serieLoteria
            sorteo = sorteoLoteria
            precio = precioLoteria
            premio = 0.0
            esPremiado = null
        }
    } else {
        val serialNumber = data.slice(0..9).toLong()
        val numeroLoteria = data.slice(11..15)
        val serieLoteria = data.slice(7..9)
        val sorteoLoteria = data.slice(2..3)



    }



    return loteria
}