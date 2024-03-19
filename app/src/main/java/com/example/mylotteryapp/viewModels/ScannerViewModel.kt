package com.example.mylotteryapp.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.crearBoletos.crearBoleto
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.domain.ScannerRepository
import com.example.mylotteryapp.models.Boleto
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScannerViewModel(
    private val scannerRepo: ScannerRepository,
    private val realmRepo: RealmRepository,
    private val realm: Realm,

    ) : ViewModel() {
    fun startScanning(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            scannerRepo.startScanning()
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    if (!data.isNullOrBlank()) {
                        Log.i("rawData", data)

                        val boleto = crearBoleto(data)
                        val result = realm
                            .query<Boleto>("numeroSerie==$0", boleto.numeroSerie).find()
                        if (result.isEmpty()) {
                            realmRepo.insertarBoleto(boleto)
                        } else {
                            message(CoroutineScope(Dispatchers.IO), context)
                        }

                    }
                }
        }
    }
}

private fun message(viewModelScope: CoroutineScope, context: Context) {
    viewModelScope.launch {
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Ya existe èste boleto",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
