package com.example.mylotteryapp.screens.firstScreen

import android.util.Log
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun datePicker(){

    // DatePicker
    var openDialog by rememberSaveable { mutableStateOf(false) }
    val formatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }

    val startYear = Calendar.getInstance().get(Calendar.YEAR).minus(3)
    val endYear = Calendar.getInstance().get(Calendar.YEAR).plus(1)
    val state = rememberDateRangePickerState(yearRange = startYear..endYear )

    val startDay by remember(openDialog) { derivedStateOf { state.selectedStartDateMillis } }
    val endDay by remember(openDialog) { derivedStateOf { state.selectedEndDateMillis } }

    AnimatedVisibility(
        visible = openDialog,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {

        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    },
                    enabled = state.selectedEndDateMillis != null
                ) {
                    Text(text = "Ok", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            modifier = Modifier,
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        state.setSelection(startDateMillis = null, endDateMillis = null)
                    }
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onPrimary)
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
                    todayContentColor = MaterialTheme.colorScheme.onPrimary,
                    todayDateBorderColor = MaterialTheme.colorScheme.onPrimary,
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