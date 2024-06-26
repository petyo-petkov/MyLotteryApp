package com.example.mylotteryapp.screens.homeScreen

import android.graphics.PorterDuff
import android.text.Layout
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.crearBoletos.fechaToRealmInstant
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.compose.common.shader.component
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.compose.common.shape.dashed
import com.patrykandpatrick.vico.compose.common.shape.markerCornered
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import com.patrykandpatrick.vico.core.common.shader.TopBottomShader
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.Shape
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.text.Typography.euro

@Composable
fun LineChart(
    realmViewModel: RealmViewModel
) {

    val data = mutableMapOf<String, Double>()
    val dateFormat = SimpleDateFormat("ddMMMyy", Locale.ENGLISH)
    val calendar = rememberSaveable { Calendar.getInstance() }

    for (i in Calendar.JANUARY..Calendar.DECEMBER) {
        with(calendar) {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.MONTH, i)
        }

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

    val dataState by rememberUpdatedState(data)
    modelProducer.tryRunTransaction {
        lineSeries { series(dataState.values) }
        updateExtras { it[labelListKey] = dataState.keys.toList() }
    }

    val axisFormatter = CartesianValueFormatter { x, chartValues, _ ->
        chartValues.model.extraStore[labelListKey][x.toInt()]
    }

    val verde = Color(0xFF43A047)
    val rojo = Color(0xFFF44336)
    val markerColor = Color(0xFFFFD54F)

    val marker = rememberDefaultCartesianMarker(
        label = rememberTextComponent(
            color = Color.Black,
            background = rememberShapeComponent(
                shape = Shape.markerCornered(Corner.FullyRounded), color = markerColor
            ),
            padding = Dimensions.of(8.dp, 4.dp),
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            minWidth = TextComponent.MinWidth.fixed(36f)
        ), guideline = rememberAxisGuidelineComponent(thickness = 0.8.dp)
    )


    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                lines = listOf(
                    rememberLineSpec(
                        shader = TopBottomShader(
                            DynamicShader.color(verde),
                            DynamicShader.color(rojo),
                        ),
                        backgroundShader = TopBottomShader(
                            DynamicShader.compose(
                                DynamicShader.component(
                                    componentSize = 2.dp,
                                    component = rememberShapeComponent(
                                        shape = Shape.Rectangle,
                                        color = verde,
                                        margins = Dimensions.of(horizontal = 0.5.dp),
                                    ),
                                    checkeredArrangement = false,
                                ),
                                DynamicShader.verticalGradient(
                                    arrayOf(Color.Black, Color.Transparent),
                                    positions = floatArrayOf(0.6f, 1f)
                                ),
                                PorterDuff.Mode.DST_IN,
                            ),
                            DynamicShader.compose(
                                DynamicShader.component(
                                    componentSize = 2.dp,
                                    component = rememberShapeComponent(
                                        shape = Shape.Rectangle,
                                        color = rojo,
                                        margins = Dimensions.of(horizontal = 0.5.dp),
                                    ),
                                    checkeredArrangement = false,
                                ), DynamicShader.verticalGradient(
                                    arrayOf(Color.Transparent, Color.Black),
                                    positions = floatArrayOf(0.1f, 1f)
                                ), PorterDuff.Mode.DST_IN
                            ),
                        ),
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
                title = "Euros  $euro",
                axis = null,
                tick = null,
                guideline = rememberLineComponent(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = remember {
                        Shape.dashed(
                            shape = Shape.Pill,
                            dashLength = 4.dp,
                            gapLength = 8.dp,
                        )
                    },
                ),

                ),
            bottomAxis = rememberBottomAxis(
                label = rememberAxisLabelComponent(color = Color.Black),
                guideline = null,
                valueFormatter = axisFormatter,
                labelRotationDegrees = 0f
            ),

            ),
        modelProducer = modelProducer,
        marker = marker,
        modifier = Modifier.padding(horizontal = 6.dp),

        )
}