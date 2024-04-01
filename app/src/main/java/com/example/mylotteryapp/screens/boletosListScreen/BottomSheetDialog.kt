package com.example.mylotteryapp.screens.boletosListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    showBottomSheet: Boolean,
    selectedTipo: Boolean,
    selectedPrecio: Boolean,
    selectedGanado: Boolean,
    onDismiss: () -> Unit,
    rangoFechasChip: () -> Unit,
    tipoChip: () -> Unit,
    precioChip: () -> Unit,
    ganadoChip: () -> Unit


){
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
//            var selectedTipo by remember { mutableStateOf(false) }
//            var selectedFecha by remember { mutableStateOf(false) }
//            var selectedGanado by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Filtrar por:")

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    FilterChip(
                        onClick = { tipoChip() },
                        label = {
                            Text("Tipo Sorteo")
                        },
                        selected = selectedTipo,
                        modifier = Modifier,
                        leadingIcon = if (selectedTipo) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.background
                        )
                    )
                    FilterChip(
                        onClick = { ganadoChip() },
                        label = {
                            Text("Ganado")
                        },
                        selected = selectedGanado,
                        leadingIcon = if (selectedGanado) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.background
                        )
                    )
                    FilterChip(
                        onClick = { precioChip() },
                        label = {
                            Text("Precio")
                        },
                        selected = selectedPrecio,
                        leadingIcon = if (selectedPrecio) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                ) {
                    AssistChip(
                        onClick = {
                            rangoFechasChip()
                        },
                        label = { Text("Rango de fechas") },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.DateRange,
                                contentDescription = "Localized description",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    )
                }
            }


        }
    }


}