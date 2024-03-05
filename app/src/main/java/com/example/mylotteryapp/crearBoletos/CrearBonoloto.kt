package com.example.mylotteryapp.crearBoletos

import com.example.mylotteryapp.models.Bonoloto
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmObject

fun crearBonoloto(data: String) : Bonoloto {

    val info = data.split(";")
    var numReintegro = 0
    val combi = info[4].split(".").drop(1)
    val fechaString = info[2].slice(5..11)


    info.filter { it.startsWith("R=") }
    .forEach {
        numReintegro = it.last().toString().toInt()
    }

    val bonoloto = Bonoloto().apply {
        tipo = "Bonoloto"
        numeroSerie = info[0].slice(2..11).toLong()
        fecha = fechaToRealmInstant(fechaString)
        combinaciones = combi.toRealmList()
        reintegro = numReintegro
        precio = 1.0
        premio = null

    }
    return bonoloto

}