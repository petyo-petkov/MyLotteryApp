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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mylotteryapp.viewModels.RealmViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoletosByDates(
    navController: NavController,
    realmViewModel: RealmViewModel,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val boletos by realmViewModel.boletosEnRangoDeFechas.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                boletos = boletos,
                scrollBehavior,
                realmViewModel
            )
        },
        floatingActionButton = {
            RangoFechasFAB(
                realmViewModel = realmViewModel,
                navController
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = MaterialTheme.colorScheme.background

    ) {

        ListBoletos(
            realmViewModel = realmViewModel,
            paddingValues = it,
            boletos = boletos

        )
        // Limpia los estados al usar gesto de volver
        BackHandler(
            enabled = true,
            onBack = {
                realmViewModel.stateCleaner()
                navController.popBackStack()
            }
        )

    }
}