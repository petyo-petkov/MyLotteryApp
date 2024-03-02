package com.example.mylotteryapp.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.models.Primitiva
import com.example.mylotteryapp.domain.ScannerRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val repo: ScannerRepository,
    private val realm: Realm

) : ViewModel() {

    private val _state = mutableStateOf("")
    val state = _state

    val boletos = realm
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

    fun startScanning() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.startScanning()
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    Log.i("tag", "Error: ${error.message}")

                }
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    if (!data.isNullOrBlank()) {
                        state.value = data
                        realm.write {
                           copyToRealm(crearBoleto(data), UpdatePolicy.ALL)
                        }


                        Log.i("rawData", data)

                    }
                }
        }
    }

}

private fun crearBoleto(data: String): RealmObject {

    val info = data.split(";")
    var re = 0
    val combi = info[4].split(".").drop(1)

    info.forEach { i ->
        if (i.startsWith("R=")) {
            re = i.last().toString().toInt()
        }

    }

    val primitiva = Primitiva().apply {
        numeroSerie = info[0].slice(2..11).toLong()
        fecha = info[2].slice(5..11)
        combinaciones = combi.toRealmList()
        reintegro = re
        precio = 1.0
        premio = null

    }

    return primitiva

}