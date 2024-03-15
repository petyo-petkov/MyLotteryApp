package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylotteryapp.R
import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.viewModels.RealmViewModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ListBoletosItem(
    boletos: List<Boletos>,
    formatter: SimpleDateFormat,
    realmViewModel: RealmViewModel,
    paddingValues: PaddingValues,
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {

        for (boleto in boletos) {

            boleto.primitivas?.let { primitivas ->
                items(primitivas) { primitiva ->
                    val date = Date(primitiva.fecha!!.epochSeconds * 1000)
                    var expandedState by remember { mutableStateOf(false) }
                    val rotationState by animateFloatAsState(
                        targetValue = if (expandedState) 180f else 0f, label = ""
                    )
                    Card(
                        onClick = { expandedState = !expandedState },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ),
                        shape = ShapeDefaults.ExtraSmall,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
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
                                        painter = painterResource(id = R.drawable.la_primitiva),
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
                                        text = primitiva.tipo,
                                        modifier = Modifier,
                                        fontSize = 20.sp,
                                    )
                                    Text(
                                        text = "${primitiva.precio} ${Typography.euro}",
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
                                        .padding(start = 96.dp, end = 12.dp, bottom = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .weight(1f),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        primitiva.combinaciones.forEachIndexed { index, combi ->
                                            Text("${index + 1}: $combi")

                                        }
                                        Text("Reintegro: ${primitiva.reintegro}")
                                        Text("Joker: ${primitiva.joker}")
                                        Text("Premio: ${primitiva.premio} ${Typography.euro}")
                                    }
                                    IconButton(
                                        onClick =  {realmViewModel.deleteBoleto(boleto.numeroSerieBoleto)} ,
                                        modifier = Modifier
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null,
                                            tint = Color.Red
                                        )
                                    }


                                }
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }
            }

            boleto.bonolotos?.let { bonolotos ->
                items(bonolotos) { bonoloto ->
                    val date = Date(bonoloto.fecha!!.epochSeconds * 1000)
                    var expandedState by remember { mutableStateOf(false) }
                    val rotationState by animateFloatAsState(
                        targetValue = if (expandedState) 180f else 0f, label = ""
                    )
                    Card(
                        onClick = { expandedState = !expandedState },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ),
                        shape = ShapeDefaults.ExtraSmall,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
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
                                        painter = painterResource(id = R.drawable.bonoloto),
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
                                        text = bonoloto.tipo,
                                        modifier = Modifier,
                                        fontSize = 20.sp,
                                    )
                                    Text(
                                        text = "${bonoloto.precio} ${Typography.euro}",
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
                                        .padding(start = 96.dp, end = 12.dp, bottom = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .weight(1f),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        bonoloto.combinaciones.forEachIndexed { index, combi ->
                                            Text("${index + 1}: $combi")

                                        }
                                        Text("Reintegro: ${bonoloto.reintegro}")
                                        Text("Premio: ${bonoloto.premio} ${Typography.euro}")
                                    }
                                    IconButton(
                                        onClick = {realmViewModel.deleteBoleto(boleto.numeroSerieBoleto)},
                                        modifier = Modifier
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null,
                                            tint = Color.Red
                                        )
                                    }


                                }
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }

            }

            boleto.euroMillones?.let { euromillones ->
                items(euromillones) { euromillon ->
                    val date = Date(euromillon.fecha!!.epochSeconds * 1000)
                    var expandedState by remember { mutableStateOf(false) }
                    val rotationState by animateFloatAsState(
                        targetValue = if (expandedState) 180f else 0f, label = ""
                    )
                    Card(
                        onClick = { expandedState = !expandedState },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ),
                        shape = ShapeDefaults.ExtraSmall,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
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
                                        painter = painterResource(id = R.drawable.euromillones),
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
                                        text = euromillon.tipo,
                                        modifier = Modifier,
                                        fontSize = 20.sp,
                                    )
                                    Text(
                                        text = "${euromillon.precio} ${Typography.euro}",
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
                                        .padding(start = 96.dp, end = 12.dp, bottom = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .weight(1f),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        euromillon.combinaciones.forEachIndexed { index, combi ->
                                            euromillon.estrellas.forEach { star ->
                                                Text("${index + 1}: $combi \u2605 $star")
                                            }


                                        }
                                        Text("El Millon: ${euromillon.elMillon}")
                                        Text("Premio: ${euromillon.premio} ${Typography.euro}")
                                    }
                                    IconButton(
                                        onClick = {realmViewModel.deleteBoleto(boleto.numeroSerieBoleto)},
                                        modifier = Modifier
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null,
                                            tint = Color.Red
                                        )
                                    }


                                }
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }

            }

            boleto.gordos?.let { gordos ->
                items(gordos) { gordo ->
                    val date = Date(gordo.fecha!!.epochSeconds * 1000)
                    var expandedState by remember { mutableStateOf(false) }
                    val rotationState by animateFloatAsState(
                        targetValue = if (expandedState) 180f else 0f, label = ""
                    )
                    Card(
                        onClick = { expandedState = !expandedState },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ),
                        shape = ShapeDefaults.ExtraSmall,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
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
                                        painter = painterResource(id = R.drawable.el_godo),
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
                                        text = gordo.tipo,
                                        modifier = Modifier,
                                        fontSize = 20.sp,
                                    )
                                    Text(
                                        text = "${gordo.precio} ${Typography.euro}",
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
                                        .padding(start = 96.dp, end = 12.dp, bottom = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .weight(1f),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        gordo.combinaciones.forEachIndexed { index, combi ->
                                            gordo.numeroClave.forEach { clave ->
                                                Text("${index + 1}: $combi + $clave")
                                            }


                                        }
                                        Text("Premio: ${gordo.premio} ${Typography.euro}")
                                    }
                                    IconButton(
                                        onClick = {realmViewModel.deleteBoleto(boleto.numeroSerieBoleto)},
                                        modifier = Modifier
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null,
                                            tint = Color.Red
                                        )
                                    }


                                }
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }

            }

            boleto.euroDreams?.let { euroDreams ->
                items(euroDreams) { dream ->
                    val date = Date(dream.fecha!!.epochSeconds * 1000)
                    var expandedState by remember { mutableStateOf(false) }
                    val rotationState by animateFloatAsState(
                        targetValue = if (expandedState) 180f else 0f, label = ""
                    )
                    Card(
                        onClick = { expandedState = !expandedState },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ),
                        shape = ShapeDefaults.ExtraSmall,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
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
                                        painter = painterResource(id = R.drawable.euro_dreams),
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
                                        text = dream.tipo,
                                        modifier = Modifier,
                                        fontSize = 20.sp,
                                    )
                                    Text(
                                        text = "${dream.precio} ${Typography.euro}",
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
                                        .padding(start = 96.dp, end = 12.dp, bottom = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .weight(1f),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        dream.combinaciones.forEachIndexed { index, combi ->
                                            dream.dreams.forEach { numeroDream ->
                                                Text("${index + 1}: $combi + $numeroDream")
                                            }


                                        }
                                        Text("Premio: ${dream.premio} ${Typography.euro}")
                                    }
                                    IconButton(
                                        onClick = { realmViewModel.deleteBoleto(boleto.numeroSerieBoleto) },
                                        modifier = Modifier
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null,
                                            tint = Color.Red
                                        )
                                    }


                                }
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }

            }

            boleto.loterias?.let { loterias ->
                items(loterias) { loteria ->
                    val date = Date(loteria.fecha!!.epochSeconds * 1000)
                    var expandedState by remember { mutableStateOf(false) }
                    val rotationState by animateFloatAsState(
                        targetValue = if (expandedState) 180f else 0f, label = ""
                    )

                    Card(
                        onClick = { expandedState = !expandedState },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ),
                        shape = ShapeDefaults.ExtraSmall,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
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
                                        painter = painterResource(id = R.drawable.loteria_nacional),
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
                                        text = loteria.tipo,
                                        modifier = Modifier,
                                        fontSize = 20.sp,
                                    )
                                    Text(
                                        text = "${loteria.precio} ${Typography.euro}",
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
                                        .padding(start = 96.dp, end = 12.dp, bottom = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .weight(1f),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text("Numero: ${loteria.numero}")
                                        Text("Serie: ${loteria.serie}")
                                        Text("Sorteo: ${loteria.sorteo}")
                                        Text("Premio: ${loteria.premio} ${Typography.euro}")
                                    }
                                    IconButton(
                                        onClick = {realmViewModel.deleteBoleto(boleto.numeroSerieBoleto)},
                                        modifier = Modifier
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null,
                                            tint = Color.Red
                                        )
                                    }


                                }
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }
            }

        }

    }
}

