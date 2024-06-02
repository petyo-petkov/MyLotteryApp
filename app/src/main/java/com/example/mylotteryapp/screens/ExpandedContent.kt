package com.example.mylotteryapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.models.Boleto

@Composable
fun ExpandedContent(
    boleto: Boleto,
    onEditarPremio: () -> Unit,
    onGetResultado: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 98.dp, end = 2.dp, top = 2.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            when (boleto.tipo) {
                "Primitiva" -> {
                    boleto.combinaciones.forEachIndexed { index, combi ->
                        Text("${index + 1}: $combi")
                    }
                    Text("Reintegro: ${boleto.reintegro}")
                    Text("Joker: ${boleto.joker}")
                }

                "Bonoloto" -> {
                    boleto.combinaciones.forEachIndexed { index, combi ->
                        Text("${index + 1}: $combi")
                    }
                    Text("Reintegro: ${boleto.reintegro}")
                }

                "Euromillones" -> {
                    boleto.combinaciones.forEachIndexed { index, combi ->
                        boleto.estrellas.forEach { star ->
                            Text("${index + 1}: $combi \u2605 $star")
                        }
                    }
                    Text("El Millon: ${boleto.numeroElMillon}")
                }

                "El Gordo" -> {
                    boleto.combinaciones.forEachIndexed { index, combi ->
                        boleto.numeroClave.forEach { clave ->
                            Text("${index + 1}: $combi + $clave")
                        }
                    }
                }

                "Euro Dreams" -> {
                    boleto.combinaciones.forEachIndexed { index, combi ->
                        boleto.dreams.forEach { numeroDream ->
                            Text("${index + 1}: $combi + $numeroDream")
                        }
                    }
                }

                "Loteria Nacional" -> {
                    Text("Numero: ${boleto.numeroLoteria}")
                    Text("Sorteo: ${boleto.numSorteo}")
                    Text("IdSorteo: ${boleto.idSorteo}")
                }

                else -> {
                    Text(text = "")
                }
            }

        }
        Row(
            modifier = Modifier
        ) {
            IconButton(
                onClick = { onGetResultado() },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { onEditarPremio() },
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
