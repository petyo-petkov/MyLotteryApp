package com.example.mylotteryapp.screens.listScreen

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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.example.mylotteryapp.screens.ListBoletos
import com.example.mylotteryapp.screens.TopBar
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoletosListScreen(
    navController: NavController,
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    realmViewModel.ordenarBoletos()

    val boletos = realmViewModel.boletos.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                boletos = boletos.value,
                scrollBehavior,
                realmViewModel
            )
        },
        floatingActionButton = {
            ListScreenFAB(
                realmViewModel = realmViewModel,
                scannerViewModel = scannerViewModel,
                navController = navController
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
    }


    // Limpia los estados al usar gesto de volver
    BackHandler(
        enabled = true,
        onBack = {
            realmViewModel.stateCleaner()
            navController.popBackStack()
        }
    )


}