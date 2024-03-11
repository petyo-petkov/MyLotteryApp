package com.example.mylotteryapp.models

import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmInstant

class LoteriaNacional: EmbeddedRealmObject {

    var tipo: String = "Loteria Nacional"
    var numeroSerie: Long = 0L
    var fecha: RealmInstant? = null
    var numero: String = ""
    var serie: String = ""
    var sorteo: Int = 0
    var precio: Double = 0.0
    var premio: Double? = 0.0
    var esPremiado: Boolean? = null

}