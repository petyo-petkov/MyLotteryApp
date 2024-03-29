package com.example.mylotteryapp.screens.pantallaPrincipal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.compose.component.shape.shader.verticalGradient
import com.patrykandpatrick.vico.core.chart.values.AxisValueOverrider
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.model.CartesianChartModel
import com.patrykandpatrick.vico.core.model.LineCartesianLayerModel


@Composable
fun LineChartDark() {

    val model1 =
        CartesianChartModel(
            LineCartesianLayerModel.build {
                series(-2, 4, -3, -2.5, 1)
            },
        )


    val green = Color(0xFF81C784)
    val red = Color(0xFFE57373)

    CartesianChartHost(
        modifier = Modifier
            .padding(2.dp)
        ,
        chart =
        rememberCartesianChart(
            rememberLineCartesianLayer(
                listOf(
                    rememberLineSpec(
                        shader = DynamicShaders.color(green),
                        backgroundShader =
                        DynamicShaders
                            .verticalGradient(
                            arrayOf(
                                green.copy(alpha = 0.5f),
                                red.copy(alpha = 0.5f),


                            ),
                        ),

                    ),

                ),
                axisValueOverrider = AxisValueOverrider.fixed(maxY = 4f, maxX = 4f),
            ),
            startAxis = rememberStartAxis(label = rememberAxisLabelComponent(Color.Black)),
            bottomAxis = rememberBottomAxis(label = rememberAxisLabelComponent(color = Color.Black))
        ),

        model = model1,
    )

}
