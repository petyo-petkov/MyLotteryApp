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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.round
import kotlin.text.Typography.euro

@Destination<RootGraph>(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = { HomeFAB(scannerViewModel, navigator) },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = MaterialTheme.colorScheme.background

    ) {
        realmViewModel.getBoletos()
        realmViewModel.getSelected()

        val boletos = realmViewModel.boletos

        realmViewModel.getPremioPrecioBalance(boletos)

        val gastado = realmViewModel.gastado
        val ganado = realmViewModel.ganado
        val balance = realmViewModel.balance
        val balancePercent = (balance / ((ganado + gastado) / 2)) * 100

        val color = if (balance >= 0) {
            Color(0xFF558B2F)
            //Color.Black
        } else {
            Color.Red
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
                    title = "Gastado",
                    content = "$gastado $euro",
                    color = MaterialTheme.colorScheme.onSurface
                )
                SmallCard(
                    title = "Ganado",
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
                    .size(380.dp, 240.dp),
                color = MaterialTheme.colorScheme.onBackground,
                shape = ShapeDefaults.Large,
                shadowElevation = 6.dp
            ) {
                    BarChar(realmViewModel)

            }

        }
    }

}

