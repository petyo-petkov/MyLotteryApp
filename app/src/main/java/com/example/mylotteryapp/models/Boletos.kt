package com.example.mylotteryapp.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Boletos: RealmObject {
    var _id: ObjectId = ObjectId()
    var primitiva: RealmList<Primitiva> = realmListOf()
    var bonoloto: RealmList<Bonoloto> = realmListOf()
    var elGordo: RealmList<ElGordo> = realmListOf()
    var euroMillones: RealmList<EuroMillones> = realmListOf()

}