package com.example.mylotteryapp.screens.homeScreen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.model.CartesianChartModel
import com.patrykandpatrick.vico.core.model.ColumnCartesianLayerModel

@Preview
@Composable
fun BarChar() {
    val model1 =
        CartesianChartModel(
            ColumnCartesianLayerModel.build {
                series(-2, 4, -3, -2.5, 1)

            },
        )
    val colors = MaterialTheme.colorScheme
    val red = Color(0xFFD32F2F)
    val green = Color(0xFF388E3C)

    CartesianChartHost(
        modifier = Modifier.height(250.dp),
        chart =
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                ColumnCartesianLayer.ColumnProvider.series(
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
                itemPlacer = remember { AxisItemPlacer.Vertical.count(count = { 9 }) },
            ),
            bottomAxis = rememberBottomAxis(label = rememberAxisLabelComponent(color = Color.Black)),

            ),
        model = model1,
    )

}
