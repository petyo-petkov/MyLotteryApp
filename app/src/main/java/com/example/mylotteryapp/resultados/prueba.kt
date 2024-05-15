package com.example.mylotteryapp.resultados

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.resultados.modelos.bonoloto.ResultadosBonoloto
import com.example.mylotteryapp.viewModels.RealmViewModel
import kotlinx.coroutines.launch


//
//@Composable
//fun comprobar(
//    boleto: Boleto
//){
//    LaunchedEffect(Unit) {
//        val resultadoBonoloto = resultados<ResultadosBonoloto>("20240327", "20240327")
//
//        println(resultadoBonoloto[0].combinacion)
//        println(boleto.combinaciones)
//
//    }
//
//
//
//}
