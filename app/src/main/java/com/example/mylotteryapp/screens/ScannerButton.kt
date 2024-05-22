package com.example.mylotteryapp.screens

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mylotteryapp.viewModels.ScannerViewModel

@Composable
fun ScannerButton(scannerViewModel: ScannerViewModel, navController: NavController){
    val context: Context = LocalContext.current
    ElevatedButton(
        onClick = {
            scannerViewModel.startScanning(context)
//            coroutine.launch {
//                        delay(300)
//                        navController.navigate(SecondScreen)
//                    }

        },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        elevation = ButtonDefaults.elevatedButtonElevation(2.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.Black)
    }


}