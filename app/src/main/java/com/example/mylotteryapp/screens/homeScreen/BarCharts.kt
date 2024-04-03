package com.example.mylotteryapp.screens.homeScreen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.crearBoletos.fechaToRealmInstant
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.component.shape.toVicoShape
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.layer.ColumnCartesianLayer.ColumnProvider.Companion.series
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.columnSeries
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun BarChar(realmViewModel: RealmViewModel) {

    val data = mutableMapOf<String, Double>()
    val dateFormat = SimpleDateFormat("ddMMMyy", Locale.ENGLISH)
    val calendar = Calendar.getInstance()

    for (i in Calendar.JANUARY..Calendar.DECEMBER) {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.MONTH, i)

        val startDate = calendar.time
        val startDay = fechaToRealmInstant(dateFormat.format(startDate))

        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth)
        val endDate = calendar.time
        val endDay = fechaToRealmInstant(dateFormat.format(endDate))

        val monthName = SimpleDateFormat("MMM", Locale.getDefault()).format(startDate)

        val balance = realmViewModel.getMounthBalance(startDay, endDay)
        data[monthName] = balance
    }

    val labelListKey = remember { ExtraStore.Key<List<String>>() }

    val modelProducer = remember { CartesianChartModelProducer.build() }
    modelProducer.tryRunTransaction {
        columnSeries { series(data.values) }
        updateExtras { it[labelListKey] = data.keys.toList() }
    }
    val axisFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, chartValues, _ ->
        chartValues.model.extraStore[labelListKey][x.toInt()]
    }

/*
    val model = CartesianChartModel(ColumnCartesianLayerModel.build {
        series(data.values)
    })

    val axisFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { index, _, _ ->
        data.keys.elementAt(index.toInt())
    }
 */

    CartesianChartHost(
        modifier = Modifier.height(250.dp),
        chart =
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                series(
                    rememberLineComponent(
                        color = Color(0xFFE91E63),
                        thickness = 16.dp,
                        shape = RoundedCornerShape(4.dp).toVicoShape(),

                    ),
                ),
            ),
            startAxis =
            rememberStartAxis(
                label = rememberAxisLabelComponent(color = Color.Black),
            ),
            bottomAxis = rememberBottomAxis(
                label = rememberAxisLabelComponent(
                    color = Color.Black
                ),
                valueFormatter = axisFormatter

            ),

            ),
        //model = model,
        modelProducer = modelProducer
    )

}
