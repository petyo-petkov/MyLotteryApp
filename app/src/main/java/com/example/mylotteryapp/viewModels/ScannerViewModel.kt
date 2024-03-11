package com.example.mylotteryapp.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.crearBoletos.crearBonoloto
import com.example.mylotteryapp.crearBoletos.crearElGordo
import com.example.mylotteryapp.crearBoletos.crearEuroDreams
import com.example.mylotteryapp.crearBoletos.crearEuromillones
import com.example.mylotteryapp.crearBoletos.crearLoteriaNacional
import com.example.mylotteryapp.crearBoletos.crearPrimitiva
import com.example.mylotteryapp.domain.ScannerRepository
import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.models.Bonoloto
import com.example.mylotteryapp.models.ElGordo
import com.example.mylotteryapp.models.EuroDreams
import com.example.mylotteryapp.models.EuroMillones
import com.example.mylotteryapp.models.LoteriaNacional
import com.example.mylotteryapp.models.Primitiva
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
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

    var bolet: Boletos? by mutableStateOf(null)
        private set

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
                                    val bol =     //bol.isEmpty()
                                        realm.query<Primitiva>(
                                            "numeroSerie==$0",
                                            primitiva.numeroSerie
                                        )
                                            .find()
                                    if (true) {
                                        bolet?.primitivas?.add(primitiva)
                                        val boleto = Boletos().apply {
                                            primitivas?.add(primitiva)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)

                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }
                                }

                                "P=2" -> {
                                    val bonoloto = crearBonoloto(data)
                                    val bol =
                                        realm.query<Bonoloto>(
                                            "numeroSerie==$0",
                                            bonoloto.numeroSerie
                                        )
                                            .find()
                                    if (true) {
                                        bolet?.bonolotos?.add(bonoloto)
                                        val boleto = Boletos().apply {
                                            bonolotos?.add(bonoloto)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)

                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }
                                }

                                "P=7" -> {
                                    val euromillon = crearEuromillones(data)
                                    val bol =
                                        realm.query<EuroMillones>(
                                            "numeroSerie==$0",
                                            euromillon.numeroSerie
                                        ).find()
                                    if (true) {
                                        bolet?.euroMillones?.add(euromillon)
                                        val boleto = Boletos().apply {
                                            euroMillones?.add(euromillon)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)

                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }
                                }

                                "P=4" -> {
                                    val gordo = crearElGordo(data)
                                    val bol =
                                        realm.query<ElGordo>("numeroSerie==$0", gordo.numeroSerie)
                                            .find()
                                    if (true) {
                                        bolet?.gordos?.add(gordo)
                                        val boleto = Boletos().apply {
                                            gordos?.add(gordo)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)

                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }

                                }

                                "P=14" -> {
                                    val dream = crearEuroDreams(data)
                                    val bol =
                                        realm.query<EuroDreams>(
                                            "numeroSerie==$0",
                                            dream.numeroSerie
                                        )
                                            .find()
                                    if (true) {
                                        bolet?.euroDreams?.add(dream)
                                        val boleto = Boletos().apply {
                                            euroDreams?.add(dream)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)

                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }
                                }

                                "P=10" -> {
                                    val loteri = crearLoteriaNacional(data)
                                    val bol =
                                        realm.query<LoteriaNacional>(
                                            "numeroSerie==$0",
                                            loteri.numeroSerie
                                        )
                                            .find()
                                    if (true) {
                                        bolet?.loterias?.add(loteri)
                                        val boleto = Boletos().apply {
                                            loterias?.add(loteri)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)

                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }
                                }

                                else -> {}
                            }
                        }
                    } else if (!data.isNullOrBlank() && data.length == 20){
                        realm.write {
                            val loteri = crearLoteriaNacional(data)
                            val bol =
                                realm.query<LoteriaNacional>(
                                    "numeroSerie==$0",
                                    loteri.numeroSerie
                                )
                                    .find()
                            if (true) {
                                bolet?.loterias?.add(loteri)
                                val boleto = Boletos().apply {
                                    loterias?.add(loteri)
                                }
                                copyToRealm(boleto, UpdatePolicy.ALL)

                            } else {
                                message(CoroutineScope(Dispatchers.IO), context)
                            }
                        }
                    } else { viewModelScope.launch {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Código no valido",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } }

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
