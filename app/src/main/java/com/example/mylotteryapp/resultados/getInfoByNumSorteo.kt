package com.example.mylotteryapp.resultados

import kotlinx.serialization.json.JsonObject

suspend fun getInfoByNumSorteo(numSorteo: String): InfoSorteo {

    val urlProximosSorteos =
        "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=LNAC&num=6"
    val urlUltimosSorteos =
        "https://www.loteriasyapuestas.es/servicios/buscadorUltimosSorteosCelebradosLoteriaNacional"

    var idSorteo = ""
    var fechaSorteo = ""
    var precioSorteo = ""
    var resultados: List<JsonObject>
    var resultadoEncontrado = true

    resultados = getInfoFromURL(url = urlProximosSorteos)

    for (result in resultados) {
        val sorteoID = result.get("id_sorteo").toString()
        val sorteoNum = sorteoID.substring(8, 11)
        val fecha = result.get("fecha").toString()
        val precio = result.get("precio").toString()
        if (sorteoNum == numSorteo) {
            idSorteo = sorteoID
            fechaSorteo = fecha
            precioSorteo = precio
        } else {
            resultadoEncontrado = false
        }
    }
    if (!resultadoEncontrado) {
        resultados = getInfoFromURL(urlUltimosSorteos)
        for (result in resultados) {
            val sorteoID = result.get("id_sorteo").toString()
            val sorteoNum = sorteoID.substring(8, 11)
            val fecha = result.get("fecha_sorteo").toString()
            val precio = result.get("precioDecimo").toString()
            if (sorteoNum == numSorteo) {
                idSorteo = sorteoID
                fechaSorteo = fecha
                precioSorteo = precio
                break
            }

        }
    }
    return InfoSorteo(
        idSorteo = idSorteo,
        fechaSorteo = fechaSorteo,
        precioSorteo = precioSorteo
    )
}

data class InfoSorteo(
    val idSorteo: String,
    val fechaSorteo: String,
    val precioSorteo: String
)