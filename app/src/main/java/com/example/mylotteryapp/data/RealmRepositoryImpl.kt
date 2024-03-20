package com.example.mylotteryapp.data

import android.util.Log
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boleto
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class RealmRepositoryImpl(
    private val realm: Realm
) : RealmRepository {

    override suspend fun insertarBoleto(boleto: Boleto) {
        realm.writeBlocking{
            copyToRealm(boleto, UpdatePolicy.ALL)
        }
    }

    override suspend fun updatePremio(boleto: Boleto, valor: Double) {

        realm.writeBlocking {
            val queryBoleto = query<Boleto>("_id==$0", boleto._id).first().find()
            queryBoleto?.apply {
                premio = valor
            }

        }
    }

    override fun getBoletos(): Flow<List<Boleto>> {
        return realm.query<Boleto>().asFlow().map { it.list.sortedByDescending { it.fecha } }
    }

    override fun rangoFechas(startDay: RealmInstant, endDay: RealmInstant): Flow<List<Boleto>> {
        return realm.query<Boleto>(
            "fechaBoleto BETWEEN $0 AND $1", startDay, endDay
        )
            .asFlow().map { it.list }
    }



    override suspend fun deleteBoleto(id: ObjectId) {
        realm.write {
            val boleto = query<Boleto>("_id == $0", id).first().find()
            try {
                boleto?.let { delete(it) }
            } catch (e: Exception) {
                Log.d("realmRepoImpl", " ${e.message}")
            }
        }
    }

    override suspend fun deleteAll() {
        realm.write { deleteAll() }
    }

    override suspend fun getPrecios(): Flow<Double> = flow {
        var gastado = 0.0
        realm.writeBlocking {
            val boleto = realm.query<Boleto>().find()
            boleto.forEach { gastado += it.precio }
        }
        emit(gastado)
    }

    override suspend fun getPremio(): Flow<Double> = flow {
        var ganado = 0.0
        realm.writeBlocking {
            val boleto = realm.query<Boleto>().find()
            boleto.forEach{ ganado += it.premio!! }
        }
        emit(ganado)
    }
}
