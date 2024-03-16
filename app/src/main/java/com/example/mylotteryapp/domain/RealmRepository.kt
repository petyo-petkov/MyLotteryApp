package com.example.mylotteryapp.domain

import com.example.mylotteryapp.models.Boletos
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface RealmRepository {
    fun getBoletos(): Flow<List<Boletos>>
    fun rangoFechas(startDay: RealmInstant, endDay: RealmInstant): Flow<List<Boletos>>

    suspend fun insertarBoleto(boleto: Boletos)

    suspend fun deleteBoleto(id: ObjectId)

    suspend fun deleteAll()

}