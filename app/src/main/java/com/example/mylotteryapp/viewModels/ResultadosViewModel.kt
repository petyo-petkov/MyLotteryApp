package com.example.mylotteryapp.viewModels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.resultados.resultados
import kotlinx.coroutines.launch

class ResultadosViewModel(
    val realmViewModel: RealmViewModel
) : ViewModel() {

    val boletos = realmViewModel.boletos.value



}

