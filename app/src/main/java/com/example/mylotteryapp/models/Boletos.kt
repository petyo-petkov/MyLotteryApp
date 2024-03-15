package com.example.mylotteryapp.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Boletos: RealmObject {

    @PrimaryKey
    var numeroSerieBoleto: Long = 0L
    var fechaBoleto: RealmInstant? = null
    var primitivas: RealmList<Primitiva>? = realmListOf()
    var bonolotos: RealmList<Bonoloto>? = realmListOf()
    var gordos: RealmList<ElGordo>? = realmListOf()
    var euroMillones: RealmList<EuroMillones>? = realmListOf()
    var euroDreams: RealmList<EuroDreams>? = realmListOf()
    var loterias: RealmList<LoteriaNacional>? = realmListOf()

}