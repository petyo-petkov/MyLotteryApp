package com.example.mylotteryapp.screens.homeScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.crearBoletos.fechaToRealmInstant
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.decoration.rememberHorizontalLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.shape.rounded
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer.ColumnProvider.Companion.series
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shape.Shape
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun BarChart(realmViewModel: RealmViewModel) {


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

        val monthName = SimpleDateFormat("MMM", Locale.ENGLISH).format(startDate)

        val balance = realmViewModel.getMounthBalance(startDay, endDay).balance
        data[monthName] = balance


    }

    val labelListKey = remember { ExtraStore.Key<List<String>>() }
    val modelProducer = remember { CartesianChartModelProducer.build() }
    modelProducer.tryRunTransaction {
        columnSeries { series(data.values) }
        updateExtras { it[labelListKey] = data.keys.toList() }
    }
    val axisFormatter = CartesianValueFormatter { x, chartValues, _ ->
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
    val verde = Color(0xFF4CAF50)
    val rojo = Color(0xFFF44336)
    val colores = arrayOf(verde, rojo)



    CartesianChartHost(
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
        chart = rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider = series(
                    rememberLineComponent(
                        color = verde,
                        thickness = 18.dp,
                        shape = Shape.rounded(20.dp),

                        ),
                ),
            ),

            startAxis = rememberStartAxis(
                label = rememberAxisLabelComponent(color = Color.Black),
                titleComponent = rememberTextComponent(
                    background = rememberShapeComponent(
                        shape = Shape.Pill, color = MaterialTheme.colorScheme.background
                    ), padding = Dimensions.of(horizontal = 6.dp, vertical = 2.dp)
                ),
                title = "Euros",
            ),
            bottomAxis = rememberBottomAxis(
                label = rememberAxisLabelComponent(color = Color.Black),
                valueFormatter = axisFormatter,
            ),
            decorations = listOf(
                rememberHorizontalLine(
                    y = { 0f },
                    line = rememberLineComponent(color = Color.Red),
                ),
            ),
        ),
        //model = model,
        modelProducer = modelProducer
    )

}
