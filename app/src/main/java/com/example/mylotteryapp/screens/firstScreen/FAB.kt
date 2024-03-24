package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylotteryapp.crearBoletos.fechaToRealmInstant
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FAB(
    scannerViewModel: ScannerViewModel,
    realmViewModel: RealmViewModel,
    pagerState: PagerState
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    var showDialog by rememberSaveable { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

// DatePicker
    var openDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val formatter = remember { SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH) }

    val startYear = Calendar.getInstance().get(Calendar.YEAR).minus(3)
    val endYear = Calendar.getInstance().get(Calendar.YEAR).plus(1)
    val state = rememberDateRangePickerState(yearRange = startYear..endYear)

    val startDayString = state.selectedStartDateMillis?.let { Date(it) }
        ?.let { formatter.format(it) }
    val endDayString = state.selectedEndDateMillis?.let { Date(it) }
        ?.let { formatter.format(it) }

    val startDay = startDayString?.let { fechaToRealmInstant(it) }
    val endDay = endDayString?.let { fechaToRealmInstant(it) }


    //FAB
    AnimatedVisibility(
        visible = !realmViewModel.isExpanded,
        enter = fadeIn(animationSpec = tween(150)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        ExtendedFloatingActionButton(
            onClick = {},
            modifier = Modifier,
            shape = AbsoluteRoundedCornerShape(30.dp),
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier
                ) {
                    Icon(Icons.Filled.Menu, null)
                }
                ElevatedButton(
                    onClick = { scannerViewModel.startScanning(context) },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    shape = ButtonDefaults.elevatedShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                        MaterialTheme.colorScheme.tertiary
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)

                ) {
                    Icon(Icons.Filled.Add, null)
                }

                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier
                ) {
                    Icon(Icons.Filled.Delete, null, tint = Color.Red)
                }
            }

        }
    }

    DialogoBorrar(
        show = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = realmViewModel::deleteAllBoletos,
        mensaje = "Borrar todos los boletos?"
    )

    // ModalBottomSheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState

        ) {
            var selectedTipo by remember { mutableStateOf(false) }
            var selectedFecha by remember { mutableStateOf(false) }
            var selectedGanado by remember { mutableStateOf(false) }

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
                        onClick = { selectedTipo = !selectedTipo },
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
                    )
                    FilterChip(
                        onClick = { selectedFecha = !selectedFecha },
                        label = {
                            Text("Fecha")
                        },
                        selected = selectedFecha,
                        leadingIcon = if (selectedFecha) {
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
                    )
                    FilterChip(
                        onClick = { selectedGanado = !selectedGanado },
                        label = {
                            Text("Precio")
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
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                ) {
                    AssistChip(
                        onClick = {
                            openDatePickerDialog = true
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

    // DatePicker
    AnimatedVisibility(
        visible = openDatePickerDialog,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {

        DatePickerDialog(
            onDismissRequest = {
                openDatePickerDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDatePickerDialog = false
                        if (startDay != null && endDay != null) {
                            realmViewModel.sortByDates(startDay, endDay)
                        }
                        coroutine.launch {
                            pagerState.scrollToPage(1)
                        }
                        showBottomSheet = false
                        state.setSelection(startDateMillis = null, endDateMillis = null)

                    },
                    enabled = state.selectedEndDateMillis != null
                ) {
                    Text(text = "Ok", color = MaterialTheme.colorScheme.onSurface)
                }
            },
            modifier = Modifier,
            dismissButton = {
                TextButton(
                    onClick = {
                        openDatePickerDialog = false
                        state.setSelection(startDateMillis = null, endDateMillis = null)
                    }
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onSurface)
                }
            },
            shape = ShapeDefaults.ExtraLarge,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
        ) {
            DateRangePicker(
                state = state,
                modifier = Modifier.weight(1f),
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    todayContentColor = MaterialTheme.colorScheme.error,
                    todayDateBorderColor = MaterialTheme.colorScheme.error,
                    selectedDayContainerColor = MaterialTheme.colorScheme.secondary,
                    dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.secondary,
                    dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(
                        text = "Selecionar el rango de fechas",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                },
                headline = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Box(Modifier.padding(horizontal = 12.dp)) {
                            (if (state.selectedStartDateMillis != null)
                                state.selectedStartDateMillis?.let { formatter.format(it) }
                            else "Fecha inicial")?.let { Text(text = it, fontSize = 16.sp) }
                        }
                        Box(Modifier.padding(horizontal = 12.dp)) {
                            (if (state.selectedEndDateMillis != null)
                                state.selectedEndDateMillis?.let { formatter.format(it) }
                            else "Fecha final")?.let { Text(text = it, fontSize = 16.sp) }
                        }

                    }
                }

            )
        }

    }
}



