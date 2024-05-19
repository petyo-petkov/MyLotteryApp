package com.example.mylotteryapp.resultados

fun main() {
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

