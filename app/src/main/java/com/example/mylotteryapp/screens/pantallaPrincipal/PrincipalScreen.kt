package com.example.mylotteryapp.screens.pantallaPrincipal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import kotlin.math.round
import kotlin.text.Typography.euro


@Composable

fun PrincipalScreen(
    scannerViewModel: ScannerViewModel,
    realmViewModel: RealmViewModel,
) {
    realmViewModel.getBoletos()
    realmViewModel.getPrecios()
    realmViewModel.getPremio()

    val context = LocalContext.current
    val boletos = realmViewModel.boletos

    var gastado = 0.0
    var ganado = 0.0

    boletos.forEach {
        gastado += it.precio
        it.premio?.let { ganado += it }
    }

    val balance = ganado - gastado
    val balancePercent = (balance / ((ganado + gastado) / 2)) * 100

    val color = if (balance >= 0){
        Color(0xFF00C853)
    } else {
        Color.Red
    }

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { scannerViewModel.startScanning(context) },
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(Icons.Filled.Add, null, tint = Color.Black)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.background

    ) {


        Column(
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(68.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 68.dp),
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
                    title = "Balance en $euro",
                    content = "$balance $euro",
                    color = color

                )
                SmallCard(
                    title = "Balanceen en %",
                    content = "${round(balancePercent * 10) /10} %",
                    color = color
                )
            }

            Surface(
                modifier = Modifier
                    .size(340.dp, 240.dp),
                color = MaterialTheme.colorScheme.secondary,
                shape = ShapeDefaults.Large,
                shadowElevation = 6.dp
            ) {
                LineChartDark()

            }

        }
    }

}


