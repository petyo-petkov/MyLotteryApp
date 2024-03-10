package com.example.mylotteryapp.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Boletos: RealmObject {

    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var primitivas: RealmList<Primitiva>? = realmListOf()
    var bonolotos: RealmList<Bonoloto>? = realmListOf()
    var gordos: RealmList<ElGordo>? = realmListOf()
    var euroMillones: RealmList<EuroMillones>? = realmListOf()
    var euroDreams: RealmList<EuroDreams>? = realmListOf()


}