package com.example.mylotteryapp.screens.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.ScannerViewModel
import com.ramcosta.composedestinations.generated.destinations.BoletosListScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FABHome(
    navigator: DestinationsNavigator,
    scannerViewModel: ScannerViewModel
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    ExtendedFloatingActionButton(
        onClick = { },
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ElevatedButton(
                onClick = {
                    scannerViewModel.startScanning(context)

                    coroutine.launch {
                        delay(300)
                        navigator.navigate(BoletosListScreenDestination)

                    }

                },
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp),
                shape = ButtonDefaults.elevatedShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    MaterialTheme.colorScheme.tertiary
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(4.dp)

            ) {
                Icon(Icons.Filled.Add, null, tint = Color.Black)
            }

            IconButton(
                onClick = { navigator.navigate(BoletosListScreenDestination) },
                modifier = Modifier
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    null,
                    tint = Color.Black
                )
            }
        }
    }
}