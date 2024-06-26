package com.example.mylotteryapp.domain

import com.example.mylotteryapp.models.Boleto
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow

interface RealmRepository {

    suspend fun insertarBoleto(boleto: Boleto)

    suspend fun updatePremio(boleto: Boleto, valor: Double)

    suspend fun getBoletos(): Flow<List<Boleto>>

    suspend fun rangoFechas(startDay: RealmInstant, endDay: RealmInstant): Flow<List<Boleto>>

    fun boletosDelMes(primerDia: RealmInstant, ultimoDia: RealmInstant): List<Boleto>

    suspend fun deleteAll()

    suspend fun deleteSelecionados(boletos: List<Boleto>)

}