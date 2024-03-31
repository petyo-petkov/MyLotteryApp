package com.example.mylotteryapp.screens.boletosListScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylotteryapp.crearBoletos.fechaToRealmInstant
import com.example.mylotteryapp.viewModels.RealmViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RangoDeFechasDialog(
    realmViewModel: RealmViewModel,
    openDatePickerDialog: Boolean,
    formatter: SimpleDateFormat,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){


    val startYear = Calendar.getInstance().get(Calendar.YEAR).minus(3)
    val endYear = Calendar.getInstance().get(Calendar.YEAR).plus(1)
    val state = rememberDateRangePickerState(yearRange = startYear..endYear)

    val startDayString = state.selectedStartDateMillis?.let { Date(it) }
        ?.let { formatter.format(it) }
    val endDayString = state.selectedEndDateMillis?.let { Date(it) }
        ?.let { formatter.format(it) }

    val startDay = startDayString?.let { fechaToRealmInstant(it) }
    val endDay = endDayString?.let { fechaToRealmInstant(it) }

    // DatePicker
    AnimatedVisibility(
        visible = openDatePickerDialog,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {

        DatePickerDialog(
            onDismissRequest = {
                onDismiss()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                        if (startDay != null && endDay != null) {
                            realmViewModel.sortByDates(startDay, endDay)
                        }
                        onConfirm()

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
                        onDismiss()
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
                    selectedDayContainerColor = MaterialTheme.colorScheme.onSurface,
                    dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.onSurface,
                    dayInSelectionRangeContentColor = MaterialTheme.colorScheme.secondary
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