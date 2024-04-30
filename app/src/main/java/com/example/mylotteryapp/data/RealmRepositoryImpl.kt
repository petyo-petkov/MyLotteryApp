package com.example.mylotteryapp.data

import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boleto
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealmRepositoryImpl(
    private val realm: Realm
) : RealmRepository {

    override suspend fun insertarBoleto(boleto: Boleto) {
        realm.writeBlocking {
            copyToRealm(boleto, UpdatePolicy.ALL)
        }
    }

    override suspend fun updatePremio(boleto: Boleto, valor: Double) {
        realm.writeBlocking {
            val queryBoleto = query<Boleto>("_id==$0", boleto._id).first().find()
            queryBoleto?.apply {
                premio = valor
                if (valor > 0.0) esPremiado = true
            }

        }
    }

    override suspend fun updateIsSelected(boleto: Boleto, valor: Boolean) {
        realm.writeBlocking {
            val query = query<Boleto>("_id==$0", boleto._id).first().find()
            query?.apply {
                isSelected = valor
            }
        }
    }

    override suspend fun getBoletos(): Flow<List<Boleto>> {
        return realm.query<Boleto>().asFlow().map { result ->
            result.list.sortedByDescending { it.fecha }
        }
    }

    override suspend fun rangoFechas(startDay: RealmInstant, endDay: RealmInstant): Flow<List<Boleto>> {
        return realm.query<Boleto>(
            "fecha BETWEEN { $0 , $1 }", startDay, endDay
        )
            .asFlow().map { result ->
                result.list.sortedByDescending { it.fecha }
            }
    }

    override  fun boletosDelMes(primerDia: RealmInstant, ultimoDia: RealmInstant): List<Boleto> {
        return realm.query<Boleto>(
            "fecha BETWEEN { $0 , $1 }", primerDia, ultimoDia
        ).find()
    }

    override suspend fun deleteAll() {
        realm.write { deleteAll() }
    }

    override suspend fun deleteSelecionados(boletos: List<Boleto>) {
        realm.writeBlocking {
            val query = query<Boleto>("_id == $0", boletos.map { it._id } ).find()
            if (query.isNotEmpty()){
                delete(query)
            }

        }
    }


}
