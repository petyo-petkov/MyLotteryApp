package com.example.mylotteryapp.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList

class EuroMillones: EmbeddedRealmObject {

    var tipo: String = "Euromillones"
    var numeroSerie: Long = 0L
    var fecha: RealmInstant? = null
    var combinaciones: RealmList<String> = realmListOf()
    var estrellas: RealmList<String> = realmListOf()
    var elMillon: String = ""
    var precio: Double = 0.0
    var premio: Double? = 0.0
    var esPremiado: Boolean? = null

}