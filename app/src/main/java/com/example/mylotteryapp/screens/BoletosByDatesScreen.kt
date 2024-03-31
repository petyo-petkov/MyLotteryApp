package com.example.mylotteryapp.screens

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun BoletosByDates(
    navigator: DestinationsNavigator,
    realmViewModel: RealmViewModel
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopBar(

                boletos = realmViewModel.boletosEnRangoDeFechas
            )
        },
        floatingActionButton = {
            FabReturn(realmViewModel = realmViewModel, navigator)
        },
        floatingActionButtonPosition = FabPosition.End

    ) {

        ListBoletos(
            realmViewModel = realmViewModel,
            paddingValues = it,
            boletos = realmViewModel.boletosEnRangoDeFechas

        )

    }
}