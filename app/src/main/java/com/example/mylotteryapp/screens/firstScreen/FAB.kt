package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel

@Composable
fun FAB(scannerViewModel: ScannerViewModel) {
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            scannerViewModel.startScanning(context)
        },

        ) {
        Icon(Icons.Filled.Add, null)
    }
}
