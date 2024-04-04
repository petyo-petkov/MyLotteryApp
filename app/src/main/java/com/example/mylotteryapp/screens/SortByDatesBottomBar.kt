package com.example.mylotteryapp.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable

fun SortByDatesBottomBar(
    navigator: DestinationsNavigator,
    realmViewModel: RealmViewModel
){
    var showDialogBorrar by rememberSaveable { mutableStateOf(false) }

    BottomAppBar(
        actions = {

            IconButton(onClick = {
                navigator.popBackStack()
                realmViewModel.boletosEnRangoDeFechas = emptyList()

            }) {
                Icon(
                    imageVector =
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            IconButton(onClick = {  }) {
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
        containerColor = MaterialTheme.colorScheme.background,
        contentPadding = PaddingValues(horizontal = 8.dp)

    )
    DialogoBorrar(
        show = showDialogBorrar,
        onDismiss = { showDialogBorrar = false },
        onConfirm = realmViewModel::deleteAllBoletos,
        mensaje = "Borrar todos los boletos?"
    )

}