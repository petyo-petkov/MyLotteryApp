package com.example.mylotteryapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.models.Boletos
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

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

    fun deleteBoleto(id: ObjectId) {
        viewModelScope.launch(Dispatchers.IO) {
            realm.writeBlocking {
                val boleto = query<Boletos>("_id==$0", id).find()
                delete(boleto)
            }

        }
    }


}