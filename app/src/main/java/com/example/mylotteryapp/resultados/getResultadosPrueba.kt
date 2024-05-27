package com.example.mylotteryapp.resultados

import kotlinx.serialization.json.JsonObject


suspend fun getIdSorteo(numSorteo: String): String {

    val urlProximosSorteos =
        "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=LNAC&num=6"
    val urlUltimosSorteos =
        "https://www.loteriasyapuestas.es/servicios/buscadorUltimosSorteosCelebradosLoteriaNacional"

    var idSorteo = ""
    var resultados: List<JsonObject>
    var resultadoEncontrado = true

    resultados = getInfoLoterias(url = urlProximosSorteos)

    for (result in resultados) {
        val sorteoID = result["id_sorteo"].toString()
        val sorteoNum = result["id_sorteo"].toString().substring(9, 11)
        if (sorteoNum == numSorteo) {
            idSorteo = sorteoID
            break
        } else {
            resultadoEncontrado = false
        }

    }
    if (!resultadoEncontrado) {
        resultados = getInfoLoterias(urlUltimosSorteos)

        for (result in resultados) {
            val sorteoID = result["id_sorteo"].toString()
            val sorteoNum = result["id_sorteo"].toString().substring(9, 11)
            if (sorteoNum == numSorteo) {
                idSorteo = sorteoID
                break
            }
        }
    }

    return idSorteo
}

suspend fun main() {
    val sorteo = getIdSorteo("42")
    println(sorteo)

}