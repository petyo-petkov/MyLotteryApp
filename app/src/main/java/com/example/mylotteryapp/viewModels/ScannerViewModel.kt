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
import com.example.mylotteryapp.domain.ScannerRepository
import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.models.LoteriaNacional
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.exceptions.RealmException
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScannerViewModel(
    private val repo: ScannerRepository,
    private val realm: Realm,

    ) : ViewModel() {

    fun startScanning(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.startScanning()
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    if (!data.isNullOrBlank() && data.startsWith("A=")) {
                        Log.i("rawData", data)
                        val info = data.split(";")
                        realm.write {
                            when (info[1]) {

                                "P=1" -> {
                                    val primitiva = crearPrimitiva(data)
                                    val boleto = Boletos().apply {
                                        numeroSerieBoleto = primitiva.numeroSerie
                                        fechaBoleto = primitiva.fecha
                                        primitivas?.add(primitiva)
                                    }
                                    copyToRealm(boleto, UpdatePolicy.ALL)
                                }

                                "P=2" -> {
                                    val bonoloto = crearBonoloto(data)
                                    val boleto = Boletos().apply {
                                        numeroSerieBoleto = bonoloto.numeroSerie
                                        fechaBoleto = bonoloto.fecha
                                        bonolotos?.add(bonoloto)
                                    }
                                    copyToRealm(boleto, UpdatePolicy.ALL)
                                }

                                "P=7" -> {

                                    val euromillon = crearEuromillones(data)
                                    val boleto = Boletos().apply {
                                        numeroSerieBoleto = euromillon.numeroSerie
                                        fechaBoleto = euromillon.fecha
                                        euroMillones?.add(euromillon)
                                    }
                                    copyToRealm(boleto, UpdatePolicy.ALL)
                                }

                                "P=4" -> {
                                    val gordo = crearElGordo(data)
                                    val boleto = Boletos().apply {
                                        numeroSerieBoleto = gordo.numeroSerie
                                        fechaBoleto = gordo.fecha
                                        gordos?.add(gordo)
                                    }
                                    copyToRealm(boleto, UpdatePolicy.ALL)
                                }

                                "P=14" -> {
                                    val dream = crearEuroDreams(data)
                                    val boleto = Boletos().apply {
                                        numeroSerieBoleto = dream.numeroSerie
                                        fechaBoleto = dream.fecha
                                        euroDreams?.add(dream)
                                    }
                                    copyToRealm(boleto, UpdatePolicy.ALL)
                                }

                                "P=10" -> {
                                    val loteri = crearLoteriaQR(data)
                                    val boleto = Boletos().apply {
                                        numeroSerieBoleto = loteri.numeroSerie
                                        fechaBoleto = loteri.fecha
                                        loterias?.add(loteri)
                                    }
                                    copyToRealm(boleto, UpdatePolicy.ALL)
                                }

                                else -> {}
                            }
                        }
                    } else if (!data.isNullOrBlank() && data.length == 20) {
                        realm.write {
                            val loteri = crearLoteriaBarCode(data)
                            val bol =
                                realm.query<LoteriaNacional>(
                                    "numeroSerie==$0",
                                    loteri.numeroSerie
                                )
                                    .find()
                            if (true) {
                                Boletos().loterias?.add(loteri)
                                val boleto = Boletos().apply {
                                    fechaBoleto = loteri.fecha
                                    loterias?.add(loteri)
                                }
                                copyToRealm(boleto, UpdatePolicy.ALL)

                            } else {
                                message(CoroutineScope(Dispatchers.IO), context)
                            }
                        }
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
