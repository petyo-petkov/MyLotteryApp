package com.example.mylotteryapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boleto
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

class RealmViewModel(
    private val realmRepo: RealmRepository
) : ViewModel() {

    private val _boletos = MutableStateFlow(emptyList<Boleto>())
    val boletos: StateFlow<List<Boleto>> = _boletos

    private val _boletosEnRangoDeFechas = MutableStateFlow(emptyList<Boleto>())
    val boletosEnRangoDeFechas: StateFlow<List<Boleto>> = _boletosEnRangoDeFechas

    private val _boletosSelecionados = MutableStateFlow(emptyList<Boleto>())
    val boletosSelecionados: StateFlow<List<Boleto>> = _boletosSelecionados

    private val _balanceState = MutableStateFlow(BalanceState())
    val balanceState: StateFlow<BalanceState> = _balanceState


    fun ordenarBoletos(ordenacionState: String = "FECHA_DESC") {
        viewModelScope.launch(Dispatchers.IO) {
            _boletos.value = when (ordenacionState) {
                "TIPO_ASC" -> _boletos.value.sortedBy { it.tipo }
                "TIPO_DESC" -> _boletos.value.sortedByDescending { it.tipo }
                "PREMIO_ASC" -> _boletos.value.sortedBy { it.premio }
                "PREMIO_DESC" -> _boletos.value.sortedByDescending { it.premio }
                else -> _boletos.value.sortedByDescending { it.fecha } // FECHA_DESC por defecto
            }
        }
    }

    fun stateCleaner() {
        viewModelScope.launch(Dispatchers.IO) {
            ordenarBoletos()  // deja la lista de boletos ordenada por fecha
            _boletosEnRangoDeFechas.value =
                emptyList()  // deja la lista de boletos en rango de fechas vacia
            _boletosSelecionados.value = emptyList() // deja la lista de boletos seleccionados vacia
        }

    }

    fun getBoletos() {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.getBoletos().collect {
                _boletos.value = it
            }
        }
    }

    fun getPremioPrecioBalance(boletos: List<Boleto>) {
        viewModelScope.launch(Dispatchers.IO) {
            val ganado = boletos.sumOf { it.premio }
            val gastado = boletos.sumOf { it.precio }
            val balance = ganado - gastado
            _balanceState.value = BalanceState(
                ganado = Round(ganado),
                gastado = Round(gastado),
                balance = Round(balance),
                balancePercent = (balance / ((ganado + gastado) / 2)) * 100
            )
        }
    }

    fun getMounthBalance(primerDia: RealmInstant, ultimoDia: RealmInstant): Balance {
        return realmRepo.boletosDelMes(primerDia, ultimoDia).let { boletosDelMes ->
            Balance(ganadoDelMes = boletosDelMes.sumOf { it.premio },
                gastadoDelMes = boletosDelMes.sumOf { it.precio })
        }
    }

    fun addOrRemoveSelecionados(boleto: Boleto, orden: Orden) {
        viewModelScope.launch(Dispatchers.IO) {
            _boletosSelecionados.value = when (orden) {
                Orden.ADD -> _boletosSelecionados.value + boleto
                Orden.REMOVE -> _boletosSelecionados.value - boleto
            }

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
            val boletos = boletosSelecionados.value
            if (boletos.isNotEmpty()) {
                realmRepo.deleteSelecionados(boletos)
                _boletosSelecionados.value = emptyList()

            }
        }
    }

    fun sortByDates(startDay: RealmInstant, endDay: RealmInstant) {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.rangoFechas(startDay, endDay).collect { boletos ->
                    _boletosEnRangoDeFechas.value = boletos.sortedByDescending { it.fecha }
                }
        }
    }

}

data class BalanceState(
    val ganado: Double = 0.0,
    val gastado: Double = 0.0,
    val balance: Double = 0.0,
    val balancePercent: Double = 0.0
)

// redondea hasta las dos decimas ganado, gastado y balance
private fun Round(dato: Double): Double {
    return round(dato * 100) / 100
}

sealed class Orden {
    data object ADD : Orden()
    data object REMOVE : Orden()
}

data class Balance(val ganadoDelMes: Double, val gastadoDelMes: Double) {
    val balance: Double
        get() = ganadoDelMes - gastadoDelMes
}