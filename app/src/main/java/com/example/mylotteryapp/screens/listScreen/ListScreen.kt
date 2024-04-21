package com.example.mylotteryapp.screens.listScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.mylotteryapp.screens.ListBoletos
import com.example.mylotteryapp.screens.TopBar
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun BoletosListScreen(
    navigator: DestinationsNavigator,
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val boletos = when {
        realmViewModel.tipoState -> realmViewModel.boletos.sortedBy { it.tipo }
        realmViewModel.premioState -> realmViewModel.boletos.sortedByDescending { it.premio }
        else -> realmViewModel.boletos
    }

    BackHandler(
        enabled = true,
        onBack = {
            realmViewModel.onBackGesture()
            navigator.popBackStack()
        }
    )


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                boletos = boletos,
                scrollBehavior,
                realmViewModel
            )
        },
        floatingActionButton = {
            ListScreenFAB(
                realmViewModel = realmViewModel,
                scannerViewModel = scannerViewModel,
                navigator = navigator
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
    }


}