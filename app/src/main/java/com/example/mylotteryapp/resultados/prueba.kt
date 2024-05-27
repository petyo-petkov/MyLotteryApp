package com.example.mylotteryapp.resultados

import com.google.gson.Gson
import com.google.gson.JsonObject

fun main1() {
    val combinacionGanadoraString = "06-20-26-32-38-40 C(11) R(1)"
    val misCombinaciones = listOf("06 15 36 46 47 49", "04 20 32 44 49 12")

    // Extraer los números ganadores de la cadena
    val numerosGanadores = combinacionGanadoraString
        .substringBefore(" C") // Ignora la parte de C y R
        .split("-")
        .map { it.toInt() }
        .toSet() // Usamos un set para facilitar la comparación
    val reintegro = combinacionGanadoraString.substringAfter("R(").substringBefore(")")
    val complementario = combinacionGanadoraString.substringAfter("C(").substringBefore(")")

    // Comprobar cada combinación
    for (combinacion in misCombinaciones) {
        val numerosCombinacion = combinacion
            .split(" ")
            .map { it.toInt() }
        val coincidencias = numerosCombinacion.filter { it in numerosGanadores }

        println("Combinación: $combinacion")
        println("Coincidencias: $coincidencias")


    }
    println("Reintegro: $reintegro")
    println("Complementario: $complementario")
}


fun stringToJson(input: String): JsonObject {
    val jsonObject = JsonObject()
    val pairs = input.split(";").filter { it.isNotEmpty() }

    for (pair in pairs) {
        val keyValue = pair.split("=")
        if (keyValue.size == 2) {
            val key = keyValue[0]
            val value = keyValue[1]

            // Manejo de estructuras adicionales si es necesario
            when  {
                key.startsWith(".") -> {
                    val parts = value.split("=")
                    for (part in parts) {
                        jsonObject.addProperty(key, part)
                    }
                }

                else -> {
                    jsonObject.addProperty(key, value)
                }

            }

        }
    }

    return jsonObject
}
//fun main() {
//    val input = "A=1234002020017667254303471708513792;P=2;S=10312ABR24:1;W=0;.1=032128293543.2=040810232633.3=010307202130.4=020514222733;T=50120-1;R=3;"
//    val json = stringToJson(input)
//    for ((k,v) in json.entrySet()) {
//        println("$k: $v")
//    }
//}



// URL LIST

val numero = "60873"
val url = "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=LNAC&num=2"
val url2 =
    "https://www.loteriasyapuestas.es/servicios/premioDecimoWebParaVariosSorteos?decimo=$numero&serie=&fraccion=&importeComunEnCentimos=600&idSorteos=1238309042"