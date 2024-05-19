package com.example.mylotteryapp.screens.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mylotteryapp.navigation.SecondScreen
import com.example.mylotteryapp.viewModels.ScannerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeFAB(
    scannerViewModel: ScannerViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    ExtendedFloatingActionButton(
        onClick = { },
        modifier = Modifier,
        shape = FloatingActionButtonDefaults.largeShape,
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        Row(
            modifier = Modifier
                .size(260.dp, 50.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedButton(
                onClick = {
                    scannerViewModel.startScanning(context)
                    coroutine.launch {
                        delay(300)
                        navController.navigate(SecondScreen)
                    }
                },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                elevation = ButtonDefaults.elevatedButtonElevation(2.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.Black)
            }
            FilledIconButton(
                onClick = { navController.navigate(SecondScreen) },
                modifier = Modifier
                    .padding(start = 46.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.background)

            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }

    }

}