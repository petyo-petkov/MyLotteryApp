package com.example.mylotteryapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.data.ResultasdosRepository
import com.example.mylotteryapp.models.Boleto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class ResultadosViewModel @Inject constructor(
    private val resultadosRepository: ResultasdosRepository
) : ViewModel() {

    private val _resultado = MutableStateFlow("")
    val resultado: StateFlow<String> = _resultado

    private val _premio = MutableStateFlow("")
    val premio: StateFlow<String> = _premio

    /*
    private val _premioLoteriaNacional = MutableStateFlow("")
    val premioLoteriaNacional: StateFlow<String> = _premioLoteriaNacional

    private val _premioPrimitiva = MutableStateFlow("")
    val premioPrimitiva: StateFlow<String> = _premioPrimitiva


    // Resultados Loterias
    fun fetchTodosLosResultado(boleto: Boleto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = resultadosRepository.getTodosLosResultadoPorFecha(boleto)
            _resultado.value = result
        }
    }

    // Premio Loteria Nacional
    fun fetchPremioLoteriaNacional(boleto: Boleto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = resultadosRepository.getResultadoPorNumeroLoteria(boleto)
            _premioLoteriaNacional.value = result
        }
    }

    // Premio Primitiva
    fun fetchPremioPrimitiva(boleto: Boleto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = resultadosRepository.comprobarPremioPrimitiva(boleto)
            _premioPrimitiva.value = result
        }
    }

     */

    fun fetchPremios(boleto: Boleto) {
        viewModelScope.launch(Dispatchers.IO) {
            val fechaActual = LocalDateTime.now()
            val fechaBoleto = LocalDateTime.ofEpochSecond(
                boleto.fecha.epochSeconds,
                0,
                ZoneOffset.ofHours(3)
            )
            val esAnterior = fechaBoleto.compareTo(fechaActual)
            Log.i("esAnterior", esAnterior.toString())
            when {
                fechaBoleto.compareTo(fechaActual) < -1 -> {
                    when (boleto.tipo) {
                        "Primitiva" -> {
                            _premio.value = resultadosRepository.comprobarPremioPrimitiva(boleto)
                            _resultado.value = resultadosRepository.getTodosLosResultadoPorFecha(boleto)
                        }
                        "Loteria Nacional" -> {
                            _premio.value =
                                resultadosRepository.getResultadoPorNumeroLoteria(boleto)
                            _resultado.value = resultadosRepository.getTodosLosResultadoPorFecha(boleto)
                        }

                        else -> {
                            _premio.value = "Por implementar..."
                            _resultado.value =
                                resultadosRepository.getTodosLosResultadoPorFecha(boleto)
                        }
                    }

                }
                else -> {
                    _premio.value = "Sorteo no celebrado"
                    _resultado.value = "No celebrado"
                }

            }

        }
    }


}




