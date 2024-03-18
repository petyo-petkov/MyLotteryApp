package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel

@Composable
fun BottomBar(
    scannerViewModel: ScannerViewModel,
    realmViewModel: RealmViewModel
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val selected = realmViewModel.selectedCard

    BottomAppBar(
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Check, contentDescription = "Localized description")
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Localized description",
                    tint = Color.Red
                )
            }
        },
        modifier = Modifier,
        floatingActionButton = { FAB(scannerViewModel) },
        contentPadding = PaddingValues(horizontal = 30.dp)

    )

    DialogoBorrar(
        show = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = realmViewModel::deleteAllBoletos,
        mensaje = "Borrar todos los boletos?"
    )

}

