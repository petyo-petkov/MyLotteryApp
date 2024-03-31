package com.example.mylotteryapp.screens.boletosListScreen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                boletos = realmViewModel.boletos
            )
        },
        bottomBar = {
            BoletosListBottomBar(
                realmViewModel,
                scannerViewModel,
                navigator
            )
        },

    ) {


        ListBoletos(
            realmViewModel = realmViewModel,
            paddingValues = it,
            boletos = realmViewModel.boletos

        )
    }

}