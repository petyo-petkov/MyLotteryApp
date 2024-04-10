package com.example.mylotteryapp.screens.listScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
fun ListScreenFAB(
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


    ExtendedFloatingActionButton(
        onClick = { },
        modifier = Modifier,
        shape = FloatingActionButtonDefaults.largeShape,
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        Row(
            modifier = Modifier
                .size(260.dp, 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                onClick = {
                    navigator.popBackStack()
                    navigator.navigate(HomeScreenDestination)
                },
                modifier = Modifier,
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }

            ElevatedButton(
                onClick = { scannerViewModel.startScanning(context) },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.Black)
            }

//            AnimatedVisibility (
//                visible = realmViewModel.boletosSelecionados.isNotEmpty(),
//                enter = scaleIn() ,
//                exit = scaleOut()
//            ){
//                IconButton(onClick = { showDialogBorrar = true }
//                ) {
//                    Icon(
//                        imageVector =
//                        Icons.Filled.Delete,
//                        contentDescription = null,
//                        tint = Color.Red
//                    )
//                }
//            }

            FilledIconButton(
                onClick = {
                    showBottomSheet = true
                },
                modifier = Modifier,
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                AnimatedVisibility(
                    visible = realmViewModel.boletosSelecionados.isEmpty(),
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
                AnimatedVisibility(
                    visible = realmViewModel.boletosSelecionados.isNotEmpty(),
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    IconButton(onClick = { showDialogBorrar = true }
                    ) {
                        Icon(
                            imageVector =
                            Icons.Filled.Delete,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                }

            }
        }

    }

    DialogoBorrar(
        show = showDialogBorrar,
        onDismiss = { showDialogBorrar = false },
        onConfirm = {
            realmViewModel.deleteSelecionados()
        },
        mensaje = "Borrar boletos selecionados"
    )
    BottomSheetDialog(
        realmViewModel,
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