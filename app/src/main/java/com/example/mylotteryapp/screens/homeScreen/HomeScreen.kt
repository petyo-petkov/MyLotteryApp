package com.example.mylotteryapp.screens.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import kotlin.math.round
import kotlin.text.Typography.euro


@Composable
fun HomeScreen(
    navController: NavController,
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = { HomeFAB(
            scannerViewModel,
            navController
        ) },

        floatingActionButtonPosition = FabPosition.Center,
        containerColor = MaterialTheme.colorScheme.background

    ) {

        realmViewModel.getBoletos()
        realmViewModel.ordenarBoletos()

        val boletos by realmViewModel.boletos.collectAsState()
        realmViewModel.getPremioPrecioBalance(boletos)

        val balanceState by realmViewModel.balanceState.collectAsState()
        val ganado = balanceState.ganado
        val gastado = balanceState.gastado
        val balance = balanceState.balance
        val balancePercent = balanceState.balancePercent


        val color = when {
            balance  >= 0 -> Color(0xFF558B2F)
            else -> Color.Red
        }

        Column(
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                SmallCard(
                    title = "He gastado",
                    content = "$gastado $euro",
                    color = MaterialTheme.colorScheme.onSurface
                )
                SmallCard(
                    title = "He ganado",
                    content = "$ganado $euro",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                SmallCard(
                    title = "Balance $euro",
                    content = "$balance $euro",
                    color = color

                )
                SmallCard(
                    title = "Balance %",
                    content = "${round(balancePercent * 10) / 10} %",
                    color = color
                )
            }
            Surface(
                modifier = Modifier
                    .size(380.dp, 300.dp),
                color = MaterialTheme.colorScheme.onBackground,
                shape = ShapeDefaults.Large,
                shadowElevation = 6.dp
            ) {
                //BarChart(realmViewModel)
                LineChart(realmViewModel)
            }

        }
    }

}

