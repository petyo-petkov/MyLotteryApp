package com.example.mylotteryapp.data

import android.util.Log
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boletos
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class RealmRepositoryImpl(
    private val realm: Realm
): RealmRepository {
    override fun getBoletos(): Flow<List<Boletos>> {
        return realm.query<Boletos>().asFlow().map { it.list }
    }

    override fun rangoFechas(startDay: RealmInstant, endDay: RealmInstant): Flow<List<Boletos>> {
        return realm.query<Boletos>(
            "fechaBoleto BETWEEN $0 AND $1", startDay, endDay)
            .asFlow().map { it.list }
    }

    override suspend fun insertarBoleto(boleto: Boletos) {
        realm.write { copyToRealm(boleto) }
    }

    override suspend fun deleteBoleto(id: ObjectId) {
        realm.write {
            val boleto = query<Boletos>("_id == $0", id).first().find()
            try {
                boleto?.let { delete(it) }
            }catch (e: Exception){
                Log.d("realmRepoImpl"," ${e.message}")
            }
        }
    }

    override suspend fun deleteAll() {
        realm.write { deleteAll() }
    }
}