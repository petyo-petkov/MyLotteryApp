package com.example.mylotteryapp.screens.listScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.screens.DialogoBorrar
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.ramcosta.composedestinations.generated.destinations.BoletosByDatesDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    realmViewModel: RealmViewModel,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    navigator: DestinationsNavigator

    ) {
    val sheetState = rememberModalBottomSheetState()
    var showDialogBorrar by rememberSaveable { mutableStateOf(false) }

    var selectedTipo by remember { mutableStateOf(false) }
    var selectedGanado by remember { mutableStateOf(false) }

    // DatePicker
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            modifier = Modifier,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Filtrar por:",
                    modifier = Modifier.padding(start = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilterChip(
                        onClick = {
                            selectedTipo = !selectedTipo
                            realmViewModel.tipoState = selectedTipo
                            selectedGanado = false
                            realmViewModel.premioState = false
                        },
                        label = { Text("Tipo") },
                        selected = selectedTipo,
                        modifier = Modifier,
                        leadingIcon = {
                            if (selectedTipo)
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "check",
                                    modifier = Modifier
                                )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.background
                        )
                    )
                    FilterChip(
                        onClick = {
                            selectedGanado = !selectedGanado
                            realmViewModel.premioState = selectedGanado
                            selectedTipo = false
                            realmViewModel.tipoState = false
                        },
                        label = { Text("Premio") },
                        selected = selectedGanado,
                        leadingIcon = {
                            if (selectedGanado)
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "check",
                                    modifier = Modifier
                                )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.background
                        )
                    )
                    AssistChip(
                        onClick = {
                            showDatePickerDialog = true
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
    RangoDeFechasDialog(
        realmViewModel = realmViewModel,
        openDatePickerDialog = showDatePickerDialog,
        onDismiss = { showDatePickerDialog = false },
        onConfirm = { navigator.navigate(BoletosByDatesDestination) }
    )

}