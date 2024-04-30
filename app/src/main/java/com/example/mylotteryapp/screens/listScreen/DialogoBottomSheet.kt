package com.example.mylotteryapp.screens.listScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

    // Segmented button
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Ultimos", "Tipo", "Premiado", "Fechas")

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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Ordenar:",
                    modifier = Modifier
                )

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.padding(12.dp)
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = {
                                selectedIndex = index
                                when (selectedIndex) {
                                    0 -> {
                                        realmViewModel.ordenarBoletos()
                                        onDismiss()
                                    }

                                    1 -> {
                                        realmViewModel.ordenarBoletos("TIPO_ASC")
                                        onDismiss()
                                    }

                                    2 -> {
                                        realmViewModel.ordenarBoletos("PREMIO_DESC")
                                        onDismiss()
                                    }

                                    3 -> {
                                        showDatePickerDialog = true
                                    }
                                }

                            },
                            selected = index == selectedIndex,
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = MaterialTheme.colorScheme.inversePrimary
                            )
                        ) {
                            Text(label)
                        }
                    }
                }

                HorizontalDivider()

                // Boton Borrar Todos los boletos
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
    DialogoRangoDeFechas(
        realmViewModel = realmViewModel,
        openDatePickerDialog = showDatePickerDialog,
        onDismiss = {
            showDatePickerDialog = false
            onDismiss()
            selectedIndex = 0
            realmViewModel.ordenarBoletos()
        },
        onConfirm = { navigator.navigate(BoletosByDatesDestination) }
    )

}