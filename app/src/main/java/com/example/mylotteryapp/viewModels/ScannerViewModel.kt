package com.example.mylotteryapp.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.crearBoletos.crearBoleto
import com.example.mylotteryapp.data.ResultasdosRepository
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.domain.ScannerRepository
import com.example.mylotteryapp.models.Boleto
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val scannerRepo: ScannerRepository,
    private val realmRepo: RealmRepository,
    private val realm: Realm,
    private val resultRepo: ResultasdosRepository

    ) : ViewModel() {

    fun startScanning(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            scannerRepo.startScanning()
                .collect { data ->
                    if (!data.isNullOrBlank()) {
                        Log.i("rawData", data)
                        val boleto = crearBoleto(data, resultRepo)

                        val result =
                            realm.query<Boleto>("numeroSerie==$0", boleto.numeroSerie).find()
                        if (result.isEmpty()) {
                            realmRepo.insertarBoleto(boleto)
                        } else {
                            withContext(Dispatchers.Main){
                                message(context)
                            }

                        }

                    }

                }

        }
    }
}


private suspend fun message(context: Context) {
    withContext(Dispatchers.Main) {
        Toast.makeText(
            context,
            "Ya existe èste boleto", Toast.LENGTH_SHORT
        ).show()
    }
}
