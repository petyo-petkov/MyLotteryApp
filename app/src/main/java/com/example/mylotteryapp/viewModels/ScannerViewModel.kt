package com.example.mylotteryapp.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.crearBoletos.crearBonoloto
import com.example.mylotteryapp.crearBoletos.crearElGordo
import com.example.mylotteryapp.crearBoletos.crearEuroDreams
import com.example.mylotteryapp.crearBoletos.crearEuromillones
import com.example.mylotteryapp.crearBoletos.crearLoteriaBarCode
import com.example.mylotteryapp.crearBoletos.crearLoteriaQR
import com.example.mylotteryapp.crearBoletos.crearPrimitiva
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.domain.ScannerRepository
import io.realm.kotlin.Realm
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
                    if (!data.isNullOrBlank() && data.startsWith("A=")) {
                        Log.i("rawData", data)
                        val info = data.split(";")

                        when (info[1]) {
                            "P=1" -> {
                                crearPrimitiva(data, realm, realmRepo)
                            }

                            "P=2" -> {
                                crearBonoloto(data, realm, realmRepo)
                            }

                            "P=7" -> {
                                crearEuromillones(data, realm, realmRepo)
                            }

                            "P=4" -> {
                                crearElGordo(data, realm, realmRepo)
                            }

                            "P=14" -> {
                                crearEuroDreams(data, realm, realmRepo)

                            }

                            "P=10" -> {
                                crearLoteriaQR(data, realm, realmRepo)
                            }
                        }

                    } else if (!data.isNullOrBlank() && data.length == 20) {

                        crearLoteriaBarCode(data, realm, realmRepo)

                    } else {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Código no valido",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
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
