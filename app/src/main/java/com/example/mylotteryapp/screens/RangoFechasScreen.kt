package com.example.mylotteryapp.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun BoletosByDates(
    navigator: DestinationsNavigator,
    realmViewModel: RealmViewModel,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val boletos = realmViewModel.boletosEnRangoDeFechas.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                boletos = boletos.value,
                scrollBehavior,
                realmViewModel
            )
        },
        floatingActionButton = {
            RangoFechasFAB(
                realmViewModel = realmViewModel,
                navigator = navigator
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = MaterialTheme.colorScheme.background

    ) {

        ListBoletos(
            realmViewModel = realmViewModel,
            paddingValues = it,
            boletos = boletos.value

        )
        // Limpia los estados al usar gesto de volver
        BackHandler(
            enabled = true,
            onBack = {
                realmViewModel.stateCleaner()
                navigator.popBackStack()
            }
        )

    }
}