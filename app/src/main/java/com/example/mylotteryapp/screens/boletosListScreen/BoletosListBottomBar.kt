package com.example.mylotteryapp.screens.boletosListScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.screens.DialogoBorrar
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import com.ramcosta.composedestinations.generated.destinations.BoletosByDatesDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BoletosListBottomBar(
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel,
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current

    var showDialogBorrar by rememberSaveable { mutableStateOf(false) }

// Bottom Sheet
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTipo by remember { mutableStateOf(false) }
    var selectedPrecio by remember { mutableStateOf(false) }
    var selectedGanado by remember { mutableStateOf(false) }

// DatePicker
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val formatter = remember { SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH) }

    BottomAppBar(
        actions = {

            IconButton(onClick = { navigator.navigate(HomeScreenDestination) }) {
                Icon(
                    imageVector =
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            IconButton(onClick = { showBottomSheet = true }) {
                Icon(
                    imageVector =
                    Icons.Filled.Menu,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            IconButton(onClick = { showDialogBorrar = true }) {
                Icon(
                    imageVector =
                    Icons.Filled.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        },
        modifier = Modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { scannerViewModel.startScanning(context) },
                modifier = Modifier,
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(Icons.Filled.Add, null, tint = Color.Black)
            }
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentPadding = PaddingValues(horizontal = 8.dp)


    )
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
            navigator.navigate(BoletosByDatesDestination)
            showBottomSheet = false
        }
    )


}