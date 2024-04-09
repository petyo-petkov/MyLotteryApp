package com.example.mylotteryapp.screens.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
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
fun HomeScreenBottomBar(
    scannerViewModel: ScannerViewModel,
    navigator: DestinationsNavigator
){
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    BottomAppBar(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.tertiary,

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = {
                    scannerViewModel.startScanning(context)
                    coroutine.launch {
                        delay(300)
                        navigator.navigate(BoletosListScreenDestination)
                    }
                },
                modifier = Modifier.padding(end = 108.dp),
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.Add, null, tint = Color.Black)

            }

            IconButton(
                onClick = { navigator.navigate(BoletosListScreenDestination) },
                modifier = Modifier.padding(end = 16.dp)

            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color.Black)
            }
        }

    }

}