package com.example.mylotteryapp.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.models.Primitiva
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class RealmViewModel(
    private val realm: Realm
): ViewModel() {

    val primitiva = realm
        .query<Primitiva>()
        .asFlow()
        .map { results ->
            results.list.toList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    fun deleteBoleto(id: ObjectId){
        viewModelScope.launch {
            realm.writeBlocking {
                val boleto = query<Primitiva>("_id==$0", id).find()
                delete(boleto)
            }

        }
    }

}