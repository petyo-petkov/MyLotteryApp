package com.example.mylotteryapp.screens.homeScreen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.component.shape.shader.verticalGradient
import com.patrykandpatrick.vico.compose.component.shape.toVicoShape
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.layer.ColumnCartesianLayer.ColumnProvider.Companion.series
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.columnSeries

@Composable
fun BarChar() {

    val gastado = 3.0
    val ganado = 1.0

    val balance = ganado - gastado

    val data =
        mapOf(
            "JAN" to balance,
            "FEB" to 3.5f,
            "MAR" to 1.4f,
            "APR" to 7f,
            "MAY" to 3f,
            "JUN" to 7f,
            "JUL" to 7f,
            "AUG" to 2f,
            "SEP" to 7f,
            "OCT" to 7f,
            "NOV" to 7f,
            "DEC" to 7f

        )

    val modelProducer = remember { CartesianChartModelProducer.build() }

    val labelListKey = ExtraStore.Key<List<String>>()


    modelProducer.tryRunTransaction {
        columnSeries { series(data.values) }
        updateExtras { it[labelListKey] = data.keys.toList() }
    }

    val axisFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, chartValues, _ ->
        chartValues.model.extraStore[labelListKey][x.toInt()]
    }

//    val axisFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { index, _, _ ->
//        data.keys.elementAt(index.toInt())
//    }



    val colors = MaterialTheme.colorScheme
    val red = Color(0xFFD32F2F)
    val green = Color(0xFF388E3C)

    CartesianChartHost(
        modifier = Modifier.height(250.dp),
        chart =
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                series(
                    rememberLineComponent(
                        color = colors.tertiary,
                        thickness = 12.dp,
                        shape = RoundedCornerShape(4.dp).toVicoShape(),
                        dynamicShader = DynamicShaders.verticalGradient(arrayOf(green, red)),
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
       modelProducer = modelProducer
    )

}
