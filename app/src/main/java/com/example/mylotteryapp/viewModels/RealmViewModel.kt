package com.example.mylotteryapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.models.Boletos
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RealmViewModel(
    private val realm: Realm
) : ViewModel() {

    val boletos = realm
        .query<Boletos>()
        .asFlow()
        .map { results ->
            results.list.toList()

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )



    fun deleteAllBoletos() {
        viewModelScope.launch {
            realm.write {
                deleteAll()
            }
        }
    }

    fun deleteBoleto(numeroSerieBoleto: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            realm.writeBlocking {
                val boleto = query<Boletos>("numeroSerieBoleto == $0", numeroSerieBoleto).find()
                delete(boleto)
            }
        }
    }

    fun sortByDates(startDay: RealmInstant, endDay: RealmInstant) {
        viewModelScope.launch {
            realm.writeBlocking {
                val fechasPtimitiva = realm
                    .query<Boletos>("primitivas.fecha == $0", startDay, endDay)
                    .find()

            }
        }
    }


}