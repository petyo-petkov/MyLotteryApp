package com.example.mylotteryapp.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class EuroMillones: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var tipo: String = ""
    var numeroSerie: Long = 0L
    var fecha: String = ""
    var combinaciones: RealmList<String> = realmListOf()
    var estrellas: Int = 0
    var elMillon: String = ""
    var precio: Double = 0.0
    var premio: Double? = 0.0
    @Ignore
    var esPremiado: Boolean = false
}