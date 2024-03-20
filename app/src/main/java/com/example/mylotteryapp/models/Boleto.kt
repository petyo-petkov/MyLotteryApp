package com.example.mylotteryapp.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Boleto : RealmObject {

    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var tipo: String = ""
    var numeroSerie: Long = 0L
    var fecha: RealmInstant = RealmInstant.MIN
    var precio: Double = 0.0
    var premio: Double? = null

    var combinaciones: RealmList<String> = realmListOf()
    var reintegro: String? = ""

    // prmitiva
    var joker: String? = ""

    // el gordo
    var numeroClave: RealmList<String> = realmListOf()

    // euro dreams
    var dreams: RealmList<String> = realmListOf()

    // euro millones
    var estrellas: RealmList<String> = realmListOf()
    var numeroElMillon: String = ""

    // loterias
    var numeroLoteria: String? = ""
    var serieLoteria: String? = ""
    var sorteoLoteria: String? = ""

}