package com.example.mylotteryapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.resultados.ResultasdosRepository
import com.example.mylotteryapp.resultados.modelos.bonoloto.ResultadosBonoloto
import com.example.mylotteryapp.resultados.modelos.elGordo.ResultadosElGordo
import com.example.mylotteryapp.resultados.modelos.euroDreams.ResultadosEuroDreams
import com.example.mylotteryapp.resultados.modelos.euromillones.ResultadosEuromillones
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.ResultadosLoteriaNacional
import com.example.mylotteryapp.resultados.modelos.primitva.ResultadosPrimitiva
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultadosViewModel @Inject constructor(
    private val resultadosRepository: ResultasdosRepository
) : ViewModel() {

    private val _resultado = MutableStateFlow("")
    val resultado: StateFlow<String> = _resultado

    fun fetchResultado(boleto: Boleto, fecha: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getResultado(boleto, fecha)
            _resultado.value = result
        }
    }


    private suspend fun getResultado(boleto: Boleto, fecha: String): String {

        when (boleto.tipo) {
            "Primitiva" -> return resultadosRepository.resultados<ResultadosPrimitiva>(
                fecha
            )[0].combinacion

            "Bonoloto" -> return resultadosRepository.resultados<ResultadosBonoloto>(
                fecha
            )[0].combinacion

            "Euromillones" -> return resultadosRepository.resultados<ResultadosEuromillones>(
                fecha
            )[0].combinacion

            "El Gordo" -> return resultadosRepository.resultados<ResultadosElGordo>(fecha)[0].combinacion

            "Euro Dreams" -> return resultadosRepository.resultados<ResultadosEuroDreams>(
                fecha
            )[0].combinacion

            "Loteria Nacional" -> {
                val resultadoLoteriaNacional =
                    resultadosRepository.resultados<ResultadosLoteriaNacional>(fecha)
                val primerPremio = resultadoLoteriaNacional[0].primerPremio.decimo
                val segundoPremio = resultadoLoteriaNacional[0].segundoPremio.decimo
                //val tercerPremio: String? = resultadoLoteriaNacional[0].tercerosPremios[0].decimo
                return """
                            Primer premio: $primerPremio
                            Segundo Premio: $segundoPremio
                        """.trimIndent()
            }

            else -> return "Boleto desconocido"
        }

    }

}

