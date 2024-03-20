package com.example.mylotteryapp.domain

import com.example.mylotteryapp.models.Boleto
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface RealmRepository {
    fun getBoletos(): Flow<List<Boleto>>
    fun rangoFechas(startDay: RealmInstant, endDay: RealmInstant): Flow<List<Boleto>>

    suspend fun insertarBoleto(boleto: Boleto)

    suspend fun updatePremio(boleto: Boleto, valor: Double)

    suspend fun deleteBoleto(id: ObjectId)

    suspend fun deleteAll()

    suspend fun getPrecios(): Flow<Double>

    suspend fun getPremio(): Flow<Double>


}