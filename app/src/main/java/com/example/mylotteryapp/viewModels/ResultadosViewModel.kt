package com.example.mylotteryapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.resultados.resultados
import kotlinx.coroutines.launch

class ResultadosViewModel() : ViewModel() {


    inline fun <reified T> getResultados(fechaInicio: String, fechaFin: String) {
        viewModelScope.launch {
            val result = resultados<T>(desde = "20240501", hasta = "20240506")


        }




    }


}