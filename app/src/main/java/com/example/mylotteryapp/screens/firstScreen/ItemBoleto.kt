package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylotteryapp.R
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.viewModels.RealmViewModel
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemBoleto(
    boleto: Boleto,
    formatter: SimpleDateFormat,
    realmViewModel: RealmViewModel,
) {

    val date = Date(boleto.fecha.epochSeconds * 1000)
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )
    var selected by remember { mutableStateOf(false) }
    val haptics = LocalHapticFeedback.current

    var show by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
            .combinedClickable(
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    expandedState = !expandedState
                },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    selected = !selected
                    realmViewModel.selectedCard = selected
                    realmViewModel.boleto = boleto
                }
            ),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            if (!selected) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.errorContainer
            }

        )

    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = when (boleto.tipo) {
                            "Primitiva" -> {
                                painterResource(id = R.drawable.la_primitiva)
                            }

                            "Bonoloto" -> {
                                painterResource(id = R.drawable.bonoloto)
                            }

                            "Euromillones" -> {
                                painterResource(id = R.drawable.euromillones)
                            }

                            "El Gordo" -> {
                                painterResource(id = R.drawable.el_godo)
                            }

                            "Euro Dreams" -> {
                                painterResource(id = R.drawable.euro_dreams)
                            }

                            "Loteria Nacional" -> {
                                painterResource(id = R.drawable.loteria_nacional)
                            }

                            else -> {
                                painterResource(id = R.drawable.ic_launcher_foreground)
                            }
                        },
                        contentDescription = null
                    )
                }
                // Text container
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = formatter.format(date),
                        modifier = Modifier,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = boleto.tipo,
                        modifier = Modifier,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${boleto.precio} ${Typography.euro}",
                        modifier = Modifier,
                        fontSize = 12.sp,

                        )

                }
                // Icono final
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .rotate(rotationState)

                )
            }
            if (expandedState) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 96.dp, end = 12.dp, bottom = 2.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        when (boleto.tipo) {
                            "Primitiva" -> {
                                boleto.combinaciones.forEachIndexed { index, combi ->
                                    Text("${index + 1}: $combi")
                                }
                                Text("Reintegro: ${boleto.reintegro}")
                                Text("Joker: ${boleto.joker}")
                                Text("Premio: ${boleto.premio} ${Typography.euro}")
                            }

                            "Bonoloto" -> {
                                boleto.combinaciones.forEachIndexed { index, combi ->
                                    Text("${index + 1}: $combi")
                                }
                                Text("Reintegro: ${boleto.reintegro}")
                                Text("Premio: ${boleto.premio} ${Typography.euro}")
                            }

                            "Euromillones" -> {
                                boleto.combinaciones.forEachIndexed { index, combi ->
                                    boleto.estrellas.forEach { star ->
                                        Text("${index + 1}: $combi \u2605 $star")
                                    }
                                }
                                Text("El Millon: ${boleto.numeroElMillon}")
                                Text("Premio: ${boleto.premio} ${Typography.euro}")
                            }

                            "El Gordo" -> {
                                boleto.combinaciones.forEachIndexed { index, combi ->
                                    boleto.numeroClave.forEach { clave ->
                                        Text("${index + 1}: $combi + $clave")
                                    }
                                }
                                Text("Premio: ${boleto.premio} ${Typography.euro}")
                            }

                            "Euro Dreams" -> {
                                boleto.combinaciones.forEachIndexed { index, combi ->
                                    boleto.dreams.forEach { numeroDream ->
                                        Text("${index + 1}: $combi + $numeroDream")
                                    }
                                }
                                Text("Premio: ${boleto.premio} ${Typography.euro}")
                            }

                            "Loteria Nacional" -> {
                                Text("Numero: ${boleto.numeroLoteria}")
                                Text("Serie: ${boleto.serieLoteria}")
                                Text("Sorteo: ${boleto.sorteoLoteria}")
                                Text("Premio: ${boleto.premio} ${Typography.euro}")
                            }

                            else -> {
                                Text(text = "")
                            }
                        }


                    }
                    IconButton(
                        onClick = { show = true },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }

            }

        }
    }
    DialogoPremio(
        show = show,
        onDismiss = { show = false },
        onConfirm = {valor ->

            realmViewModel.updatePremio(boleto, valor)
            show = false

        }

    )

}

