package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.data.ResultasdosRepository
import com.example.mylotteryapp.models.Boleto
import io.realm.kotlin.types.RealmInstant
import java.text.SimpleDateFormat
import java.util.Locale

suspend fun crearLoteriaFromBarCode(data: String, resultRepo: ResultasdosRepository): Boleto {

    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val serialNumber = data.substring(0, 10).toLong()
    val numeroLoteriaNacional = data.substring(11, 16)
    val numeroSorteo = data.substring(1, 4).toInt()

    return try {
        val infoLoteria = resultRepo.getInfoLNACbyNumSorteo(numeroSorteo)
        val fechaLong = formatter.parse(infoLoteria.fechaSorteo)!!.time
        Boleto().apply {
            tipo = "Loteria Nacional"
            numeroSerie = serialNumber
            fecha = RealmInstant.from(fechaLong / 1000, 0)
            numeroLoteria = numeroLoteriaNacional
            idSorteo = infoLoteria.idSorteo
            numSorteo = numeroSorteo
            precio = infoLoteria.precioSorteo
            premio = 0.0
        }
    } catch (e: Exception) {
        println("Error al obtener la información del sorteo: ${e.message}")
        throw IllegalStateException(
            "No se pudo crear el boleto debido a un error con la información del sorteo",
            e
        )
    }

}