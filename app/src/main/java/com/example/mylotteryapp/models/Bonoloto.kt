package com.example.mylotteryapp.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Bonoloto: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var numeroSerie: Long = 0L
    var fecha: String = ""
    var combinaciones: RealmList<String> = realmListOf()
    var reintegro: Int = 0
    var precio: Double = 0.0
    var premio: Double? = 0.0
    @Ignore
    var esPremiado: Boolean = false
}