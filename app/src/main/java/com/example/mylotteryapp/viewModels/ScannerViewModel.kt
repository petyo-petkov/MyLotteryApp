package com.example.mylotteryapp.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.crearBoletos.crearBonoloto
import com.example.mylotteryapp.crearBoletos.crearPrimitiva
import com.example.mylotteryapp.domain.ScannerRepository
import com.example.mylotteryapp.models.Boletos
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val repo: ScannerRepository,
    private val realm: Realm,

    ) : ViewModel() {

    private val _state = mutableStateOf("")
    val state = _state

    fun startScanning() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.startScanning()
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    Log.i("tag", "Error: ${error.message}")

                }
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    val info = data?.split(";")
                    if (!data.isNullOrBlank()) {
                        state.value = data
                        realm.write {
                            when (info?.get(1)) {
                                "P=1" -> {
                                    //copyToRealm(crearPrimitiva(data), UpdatePolicy.ALL)
                                    val primitiva = crearPrimitiva(data)
                                    val boleto = Boletos().apply {
                                        primitivas?.add(primitiva)
                                    }
                                    copyToRealm(boleto, UpdatePolicy.ALL)
                                }

                                "P=2" -> {
                                    val bonoloto = crearBonoloto(data)
                                    val boleto = Boletos().apply {
                                        bonolotos?.add(bonoloto)
                                    }
                                    copyToRealm(boleto, UpdatePolicy.ALL)
                                }
                                else -> { }
                            }

                        }
                        Log.i("rawData", data)
                    }
                }
        }
    }

}

