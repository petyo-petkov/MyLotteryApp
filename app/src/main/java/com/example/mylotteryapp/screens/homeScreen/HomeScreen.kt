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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.rememberTextComponent
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.values.AxisValueOverrider
import com.patrykandpatrick.vico.core.model.CartesianChartModel
import com.patrykandpatrick.vico.core.model.LineCartesianLayerModel
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

    val boletos = realmViewModel.boletos

    var gastado = 0.0
    var ganado = 0.0

    boletos.forEach {
        gastado += it.precio
        it.premio?.let { ganado += it }
    }

    val balance = ganado - gastado
    val balancePercent = (balance / ((ganado + gastado) / 2)) * 100

    val color = if (balance >= 0) {
        Color(0xFF00C853)
    } else {
        Color.Red
    }

    Scaffold(
        modifier = Modifier,
        floatingActionButton = { FABHome(navigator, scannerViewModel ) },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = MaterialTheme.colorScheme.surface

    ) {
        realmViewModel.getBoletos()
        realmViewModel.getPrecios()
        realmViewModel.getPremio()

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
                color = MaterialTheme.colorScheme.primary,
                shape = ShapeDefaults.Large,
                shadowElevation = 6.dp
            ) {
                //LineChartDark()
                BarChar()

            }



        }
    }

}
