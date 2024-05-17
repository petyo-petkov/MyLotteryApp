package com.example.mylotteryapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.resultados.modelos.bonoloto.ResultadosBonoloto
import com.example.mylotteryapp.resultados.modelos.elGordo.ResultadosElGordo
import com.example.mylotteryapp.resultados.modelos.euroDreams.ResultadosEuroDreams
import com.example.mylotteryapp.resultados.modelos.euromillones.ResultadosEuromillones
import com.example.mylotteryapp.resultados.modelos.loteriaNacional.ResultadosLoteriaNacional
import com.example.mylotteryapp.resultados.modelos.primitva.ResultadosPrimitiva
import com.example.mylotteryapp.resultados.resultados
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResultadosViewModel : ViewModel() {

    private val _resultado = MutableStateFlow<String>("")
    val resultado: StateFlow<String> = _resultado

    fun fetchResultado(boleto: Boleto, fechaInicio: String, fechaFin: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getResultado(boleto, fechaInicio, fechaFin)
            _resultado.value = result
        }
    }


    suspend fun getResultado(boleto: Boleto, fechaInicio: String, fechaFin: String): String {

        when (boleto.tipo) {
            "Primitiva" -> return resultados<ResultadosPrimitiva>(
                fechaInicio, fechaFin
            )[0].combinacion

            "Bonoloto" -> return resultados<ResultadosBonoloto>(
                fechaInicio, fechaFin
            )[0].combinacion

            "Euromillones" -> return resultados<ResultadosEuromillones>(
                fechaInicio, fechaFin
            )[0].combinacion

            "El Gordo" -> return resultados<ResultadosElGordo>(fechaInicio, fechaFin)[0].combinacion

            "Euro Dreams" -> return resultados<ResultadosEuroDreams>(
                fechaInicio, fechaFin
            )[0].combinacion

            "Loteria Nacional" -> {
                val resultadoLoteriaNacional =
                    resultados<ResultadosLoteriaNacional>(fechaInicio, fechaFin)
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

