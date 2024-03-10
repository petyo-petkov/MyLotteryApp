package com.example.mylotteryapp.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList

class EuroDreams : EmbeddedRealmObject {

    var tipo: String = "Euro Dreams"
    var numeroSerie: Long = 0L
    var fecha: RealmInstant? = null
    var combinaciones: RealmList<String> = realmListOf()
    var dreams: RealmList<String> = realmListOf()
    var precio: Double = 0.0
    var premio: Double? = 0.0
    var esPremiado: Boolean? = null
}
