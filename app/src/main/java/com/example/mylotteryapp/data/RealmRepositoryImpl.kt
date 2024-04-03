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
        return realm.query<Boleto>().asFlow().map { result ->
            result.list.sortedByDescending { boleto ->
                boleto.fecha
            }
        }
    }

    override fun rangoFechas(startDay: RealmInstant, endDay: RealmInstant): Flow<List<Boleto>> {
        return realm.query<Boleto>(
            "fecha BETWEEN { $0 , $1 }", startDay, endDay
        )
            .asFlow().map { result ->
                result.list.sortedByDescending { boleto ->
                    boleto.fecha
                }
            }
    }

    override fun balanceMes(primerDia: RealmInstant, ultimoDia: RealmInstant): List<Boleto> {
        return realm.query<Boleto>(
            "fecha BETWEEN { $0 , $1 }", primerDia, ultimoDia
        ).find()
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

    override suspend fun getPremioPrecioBalance(boletos: List<Boleto>): Flow<Triple<Double, Double, Double>> = flow {
        val ganadoFlow = boletos.sumOf { it.premio }
        val gastadoFlow = boletos.sumOf { it.precio }
        val balanceFlow = ganadoFlow - gastadoFlow

        emit(Triple(ganadoFlow, gastadoFlow, balanceFlow))
    }
}
