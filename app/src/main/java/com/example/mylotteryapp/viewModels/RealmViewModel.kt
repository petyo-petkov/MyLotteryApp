package com.example.mylotteryapp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boleto
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RealmViewModel(
    private val realmRepo: RealmRepository
) : ViewModel() {

    var isExpanded by mutableStateOf(false)

    var tipoState by mutableStateOf(false)
    var premioState by mutableStateOf(false)

    var boleto: Boleto by mutableStateOf(Boleto())
    var boletos by mutableStateOf(emptyList<Boleto>())
    var boletosEnRangoDeFechas by mutableStateOf(emptyList<Boleto>())
    var boletosSelecionados by mutableStateOf(emptyList<Boleto>())

    var gastado by mutableDoubleStateOf(0.0)
    var ganado by mutableDoubleStateOf(0.0)
    var balance by mutableDoubleStateOf(0.0)



    fun getBoletos() {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.getBoletos().collect {
                boletos = it
            }
        }
    }

    fun getPremioPrecioBalance(boletos: List<Boleto>) {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.getPremioPrecioBalance(boletos)
                .collect { (ganadoFlow, gastadoFlow, balanceFlow) ->
                    gastado = gastadoFlow
                    ganado = ganadoFlow
                    balance = balanceFlow
                }
        }
    }

    fun getMounthBalance(primerDia: RealmInstant, ultimoDia: RealmInstant): Double {
        val boletos = realmRepo.balanceMes(primerDia, ultimoDia)
        val ganado = boletos.sumOf { it.premio }
        val gastado = boletos.sumOf { it.precio }
        val balance = ganado - gastado

        return balance

    }

    fun getSelected() {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.getSelected().collect {
                boletosSelecionados = it
            }
        }
    }

    fun isSelected(boleto: Boleto, valor: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.updateIsSelected(boleto, valor)

        }
    }

    fun updatePremio(boleto: Boleto, valor: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.updatePremio(boleto, valor)
        }
    }

    fun deleteAllBoletos() {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.deleteAll()

        }
    }

    fun deleteSelecionados() {
        viewModelScope.launch {
            realmRepo.deleteSelecionados()
        }
    }

    fun sortByDates(startDay: RealmInstant, endDay: RealmInstant) {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.rangoFechas(startDay, endDay)
                .collect {
                    boletosEnRangoDeFechas = it
                }
        }
    }


}