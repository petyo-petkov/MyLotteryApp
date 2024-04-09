package com.example.mylotteryapp.screens.boletosListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.screens.DialogoBorrar
import com.example.mylotteryapp.viewModels.RealmViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    realmViewModel: RealmViewModel,
    showBottomSheet: Boolean,
    selectedTipo: Boolean,
    selectedPrecio: Boolean,
    selectedGanado: Boolean,
    onDismiss: () -> Unit,
    rangoFechasChip: () -> Unit,
    tipoChip: () -> Unit,
    precioChip: () -> Unit,
    ganadoChip: () -> Unit


) {
    val sheetState = rememberModalBottomSheetState()
    var showDialogBorrar by rememberSaveable { mutableStateOf(false) }


    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            modifier = Modifier,
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
                    .padding(start = 12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Filtrar por:")

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                    AssistChip(
                        onClick = {
                            rangoFechasChip()
                        },
                        label = { Text("Fechas") },

                        )
                }
                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { showDialogBorrar = true },
                        modifier = Modifier
                            .padding(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF9A9A)
                        )

                    ) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Borrar todo",
                                color = Color.Black
                            )

                        }
                    }

                }
            }

        }
    }
    DialogoBorrar(
        show = showDialogBorrar,
        onDismiss = { showDialogBorrar = false },
        onConfirm = { realmViewModel.deleteAllBoletos() },
        mensaje = "Borrar todos los boletos?"
    )

}