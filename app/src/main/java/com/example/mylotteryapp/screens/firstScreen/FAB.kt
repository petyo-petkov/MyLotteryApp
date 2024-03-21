package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel

@Composable
fun FAB(scannerViewModel: ScannerViewModel, realmViewModel: RealmViewModel) {
    val context = LocalContext.current
    var showDialog by rememberSaveable { mutableStateOf(false) }
    ExtendedFloatingActionButton(
        onClick = {

        },
        modifier = Modifier,
        shape = FloatingActionButtonDefaults.largeShape

    ) {
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(Icons.Filled.Menu, null)
        }
        FilledIconButton(
            onClick = { scannerViewModel.startScanning(context) },
            modifier = Modifier.padding(horizontal = 26.dp, vertical = 4.dp),

            ) {
            Icon(Icons.Filled.Add, null)
        }

        IconButton(
            onClick = { showDialog = true }
        ) {
            Icon(Icons.Filled.Delete, null, tint = Color.Red)
        }

    }
    DialogoBorrar(
        show = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = realmViewModel::deleteAllBoletos,
        mensaje = "Borrar todos los boletos?"
    )
}

