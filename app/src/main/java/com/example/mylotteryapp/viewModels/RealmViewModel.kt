package com.example.mylotteryapp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boletos
import io.realm.kotlin.Realm
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class RealmViewModel(
    private val realm: Realm,
    private val realmRepo: RealmRepository
) : ViewModel() {
/*
    val boletos1 = realm
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

 */

    var boletos by mutableStateOf(emptyList<Boletos>())

    init {
        viewModelScope.launch {
            realmRepo.getBoletos().collect{
                boletos = it
            }
        }
    }



    fun deleteAllBoletos() {
        viewModelScope.launch {
            realmRepo.deleteAll()
        }
    }

    fun deleteBoleto(id: ObjectId) {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.deleteBoleto(id)
        }
    }

    fun sortByDates(startDay: RealmInstant, endDay: RealmInstant) {
        viewModelScope.launch {
           realmRepo.rangoFechas(startDay, endDay)
        }
    }


}