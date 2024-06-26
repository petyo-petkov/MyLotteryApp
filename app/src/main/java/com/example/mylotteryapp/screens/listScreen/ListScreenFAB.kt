package com.example.mylotteryapp.screens.listScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mylotteryapp.navigation.FirstScreen
import com.example.mylotteryapp.screens.DialogoBorrar
import com.example.mylotteryapp.screens.ScannerButton
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel


@Composable
fun ListScreenFAB(
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel,
    navController: NavController
) {
    val boletosSelecionados by realmViewModel.boletosSelecionados.collectAsState()

    var showDialogBorrar by rememberSaveable { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

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
            // Back Button
            FilledIconButton(
                onClick = {
                    navController.navigate(FirstScreen)
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

            // Scanner Button
           ScannerButton(scannerViewModel, navController)

            // Menu Button
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
        navController = navController

    )


}