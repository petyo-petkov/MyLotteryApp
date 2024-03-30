package com.example.mylotteryapp.screens.boletosListScreen

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylotteryapp.MyApp
import com.example.mylotteryapp.presentation.viewModelFactory
import com.example.mylotteryapp.screens.ListBoletos
import com.example.mylotteryapp.screens.TopBar
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun BoletosListScreen(
    navigator: DestinationsNavigator,
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel
) {

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopBar(
                realmViewModel = realmViewModel,
                boletos = realmViewModel.boletos
            )
        },
        floatingActionButton = {
            FAB(
                scannerViewModel = scannerViewModel,
                realmViewModel = realmViewModel,
                navigator
            )
        },
        floatingActionButtonPosition = FabPosition.Center

    ) {

        ListBoletos(
            realmViewModel = realmViewModel,
            paddingValues = it,
            boletos = realmViewModel.boletos

        )
    }
}