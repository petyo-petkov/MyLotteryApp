package com.example.mylotteryapp.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.crearBoletos.crearBoletoPrimitiva
import com.example.mylotteryapp.domain.ScannerRepository
import io.realm.kotlin.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val repo: ScannerRepository,
    private val realm: Realm

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
                    if (!data.isNullOrBlank()) {
                        state.value = data
                        realm.write {
                            //copyToRealm(crearBoleto(data), UpdatePolicy.ALL)
                            copyToRealm(crearBoletoPrimitiva(data))
                        }
                        Log.i("rawData", data)
                    }
                }
        }
    }

}

