package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FAB(
    scannerViewModel: ScannerViewModel,
    realmViewModel: RealmViewModel,
    pagerState: PagerState
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    var showDialogBorrar by rememberSaveable { mutableStateOf(false) }

// Bottom Sheet
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTipo by remember { mutableStateOf(false) }
    var selectedPrecio by remember { mutableStateOf(false) }
    var selectedGanado by remember { mutableStateOf(false) }

// DatePicker
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val formatter = remember { SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH) }


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
                        MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)

                ) {
                    Icon(Icons.Filled.Add, null, tint = Color.Black)
                }

                IconButton(
                    onClick = { showDialogBorrar = true },
                    modifier = Modifier
                ) {
                    Icon(Icons.Filled.Delete, null, tint = Color.Red)
                }
            }

        }
    }

    DialogoBorrar(
        show = showDialogBorrar,
        onDismiss = { showDialogBorrar = false },
        onConfirm = realmViewModel::deleteAllBoletos,
        mensaje = "Borrar todos los boletos?"
    )

    BottomSheetDialog(
        showBottomSheet = showBottomSheet,
        selectedTipo = selectedTipo,
        selectedPrecio = selectedPrecio,
        selectedGanado = selectedGanado,
        onDismiss = { showBottomSheet = false },
        rangoFechasChip = { showDatePickerDialog = true },
        tipoChip = { selectedTipo = !selectedTipo },
        precioChip = { selectedPrecio = !selectedPrecio },
        ganadoChip = { selectedGanado = !selectedGanado }
    )

    RangoDeFechasDialog(
        realmViewModel = realmViewModel,
        openDatePickerDialog = showDatePickerDialog,
        formatter = formatter,
        onDismiss = { showDatePickerDialog = false },
        onConfirm = {
            coroutine.launch {
                pagerState.animateScrollToPage(
                    page = 1,
                    animationSpec = tween(durationMillis = 500)
                )
            }
            showBottomSheet = false
        }
    )
}
