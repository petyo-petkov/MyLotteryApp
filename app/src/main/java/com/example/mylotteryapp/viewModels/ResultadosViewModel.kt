package com.example.mylotteryapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.data.ResultasdosRepository
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.ResultadosLoteriaNacional
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ResultadosViewModel @Inject constructor(
    private val resultadosRepository: ResultasdosRepository,
    private val realmRepository: RealmRepository,
) : ViewModel() {

    private val _resultado = MutableStateFlow("")
    val resultado: StateFlow<String> = _resultado

    private val _premio = MutableStateFlow("")
    val premio: StateFlow<String> = _premio


    fun fetchPremios(boleto: Boleto) {
        viewModelScope.launch(Dispatchers.IO) {
//            val formatterResultados = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
//            val fechaBoleto = formatterResultados.format(Date(boleto.fecha.epochSeconds * 1000))
            val formatterResultados = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH)
            val fechaBoletoDate = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(boleto.fecha.epochSeconds),
                ZoneId.systemDefault()
            )
            val fechaBoleto = fechaBoletoDate.format(formatterResultados)

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val fechaActual = LocalDateTime.now()
            val fechaCierre = LocalDateTime.parse(boleto.cierre, formatter)
            val esAnterior = fechaCierre.isBefore(fechaActual)
            when {
                esAnterior -> {
                    when (boleto.gameID) {
                        "LAPR" -> {
                            _premio.value = resultadosRepository.comprobarPremioLAPR(boleto).premio
                            _resultado.value =
                                resultadosRepository.comprobarPremioLAPR(boleto).combinacion
                            if (_premio.value.isNotEmpty()) realmRepository.updatePremio(boleto, _premio.value.toDouble())
                        }
                        "BONO" -> {
                            _premio.value = resultadosRepository.comprobarPremioBONO(boleto).premio
                            _resultado.value =
                                resultadosRepository.comprobarPremioBONO(boleto).combinacion
                            if (_premio.value.isNotEmpty()) realmRepository.updatePremio(boleto, _premio.value.toDouble())
                        }

                        "EDMS" -> {
                            _premio.value = resultadosRepository.comprobarPremioEDMS(boleto).premio
                            _resultado.value =
                                resultadosRepository.comprobarPremioEDMS(boleto).combinacion
                            if (_premio.value.isNotEmpty()) realmRepository.updatePremio(boleto, _premio.value.toDouble())
                        }
                        "EMIL" -> {
                            _premio.value = resultadosRepository.comprobarPremioEMIL(boleto).premio
                            _resultado.value =
                                resultadosRepository.comprobarPremioEMIL(boleto).combinacion
                            if (_premio.value.isNotEmpty()) realmRepository.updatePremio(boleto, _premio.value.toDouble())
                        }

                        "LNAC" -> {
                            _premio.value =
                                resultadosRepository.getPremioLoteriaNacional(boleto).toString()
                            _resultado.value =
                                resultadosRepository.getInfoPorFechas<ResultadosLoteriaNacional>(
                                    fechaBoleto,
                                    fechaBoleto
                                )[0].primerPremio.decimo
                            if (_premio.value.isNotEmpty()) realmRepository.updatePremio(boleto, _premio.value.toDouble())

                        }

                        else -> {
                            _premio.value = "Por implementar..."

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




