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
import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.models.Bonoloto
import com.example.mylotteryapp.models.ElGordo
import com.example.mylotteryapp.models.EuroDreams
import com.example.mylotteryapp.models.EuroMillones
import com.example.mylotteryapp.models.LoteriaNacional
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
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
                    if (!data.isNullOrBlank() && data.startsWith("A=")) {
                        Log.i("rawData", data)
                        val info = data.split(";")

                        when (info[1]) {
                            "P=1" -> { crearPrimitiva(data, realm, realmRepo) }
                            "P=2" -> { crearBonoloto(data, realm, realmRepo) }
                            "P=7" -> { crearEuromillones(data, realm, realmRepo) }
                            "P=4" -> { crearElGordo(data, realm, realmRepo) }
                            "P=14" -> { crearEuroDreams(data, realm, realmRepo) }
                            "P=10" -> { crearLoteriaQR(data, realm, realmRepo) }
                        }
                        /*

                        realm.write {
                            when (info[1]) {

                                "P=1" -> {
                                    val primitiva = crearPrimitiva(data)
                                    Boletos().apply { primitivas?.add(primitiva) }

                                    val result = realm.query<Primitiva>(
                                        "numeroSerie==$0",
                                        primitiva.numeroSerie
                                    ).find()

                                    if (result.isEmpty()) {
                                        val boleto = Boletos().apply {
                                            fechaBoleto = primitiva.fecha
                                            primitivas?.add(primitiva)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)
                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }
                                }

                                "P=2" -> {
                                    val bonoloto = crearBonoloto(data)
                                    Boletos().apply { bonolotos?.add(bonoloto) }

                                    val result = realm.query<Bonoloto>(
                                        "numeroSerie==$0",
                                        bonoloto.numeroSerie
                                    ).find()

                                    if (result.isEmpty()) {
                                        val boleto = Boletos().apply {
                                            fechaBoleto = bonoloto.fecha
                                            bonolotos?.add(bonoloto)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)
                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }
                                }

                                "P=7" -> {

                                    val euromillon = crearEuromillones(data)
                                    Boletos().apply { euroMillones?.add(euromillon) }

                                    val result = realm.query<EuroMillones>(
                                        "numeroSerie==$0",
                                        euromillon.numeroSerie
                                    ).find()

                                    if (result.isEmpty()) {
                                        val boleto = Boletos().apply {
                                            fechaBoleto = euromillon.fecha
                                            euroMillones?.add(euromillon)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)
                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }
                                }

                                "P=4" -> {
                                    val gordo = crearElGordo(data)
                                    Boletos().apply { gordos?.add(gordo) }

                                    val result = realm.query<ElGordo>(
                                        "numeroSerie==$0",
                                        gordo.numeroSerie
                                    ).find()

                                    if (result.isEmpty()) {
                                        val boleto = Boletos().apply {
                                            fechaBoleto = gordo.fecha
                                            gordos?.add(gordo)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)
                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }
                                }

                                "P=14" -> {
                                    val dream = crearEuroDreams(data)
                                    Boletos().apply { euroDreams?.add(dream) }

                                    val result = realm.query<EuroDreams>(
                                        "numeroSerie==$0",
                                        dream.numeroSerie
                                    ).find()

                                    if (result.isEmpty()) {
                                        val boleto = Boletos().apply {
                                            fechaBoleto = dream.fecha
                                            euroDreams?.add(dream)
                                        }
                                        copyToRealm(boleto, UpdatePolicy.ALL)
                                    } else {
                                        message(CoroutineScope(Dispatchers.IO), context)
                                    }
                                }

                                "P=10" -> {
                                    val loteri = crearLoteriaQR(data)
                                    Boletos().apply { loterias?.add(loteri) }

                                    val result = realm.query<LoteriaNacional>(
                                        "numeroSerie==$0",
                                        loteri.numeroSerie
                                    ).find()

                                    if (result.isEmpty()) {
                                        val boleto = Boletos().apply {
                                            fechaBoleto = loteri.fecha
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

                         */

                    } else if (!data.isNullOrBlank() && data.length == 20) {

                        crearLoteriaBarCode(data, realm, realmRepo)

                        /*
                                            realm.write {
                            val loteri = crearLoteriaBarCode(data)
                            Boletos().apply { loterias?.add(loteri) }

                            val result = realm.query<LoteriaNacional>(
                                    "numeroSerie==$0",
                                    loteri.numeroSerie).find()

                            if (result.isEmpty()) {
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

 */


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
