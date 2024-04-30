package com.example.mylotteryapp.screens.listScreen

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ListScreenFAB(
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    val boletosSelecionados by realmViewModel.boletosSelecionados.collectAsState()

    var showDialogBorrar by rememberSaveable { mutableStateOf(false) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

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
                    realmViewModel.ordenarBoletos()
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

            FilledIconButton(
                onClick = { showBottomSheet = !showBottomSheet },
                modifier = Modifier,
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                AnimatedVisibility(
                    visible = boletosSelecionados.isEmpty(),
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
                    visible = boletosSelecionados.isNotEmpty(),
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    IconButton(
                        onClick = { showDialogBorrar = !showDialogBorrar }
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
        Log.i("lista", "${boletosSelecionados.size}")
    }

    DialogoBorrar(
        show = showDialogBorrar,
        onDismiss = { showDialogBorrar = false },
        onConfirm = realmViewModel::deleteSelecionados,
        mensaje = "Borrar boletos selecionados"
    )
    BottomSheetDialog(
        realmViewModel,
        showBottomSheet = showBottomSheet,
        onDismiss = { showBottomSheet = false },
        navigator = navigator

    )


}